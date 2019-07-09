/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.fabric.netty.handlers;

import com.liferay.petra.concurrent.BaseFutureListener;
import com.liferay.petra.concurrent.FutureListener;
import com.liferay.petra.concurrent.NoticeableFuture;
import com.liferay.petra.concurrent.NoticeableFutureConverter;
import com.liferay.petra.process.ClassPathUtil;
import com.liferay.petra.process.PathHolder;
import com.liferay.petra.process.ProcessCallable;
import com.liferay.petra.process.ProcessConfig;
import com.liferay.petra.process.ProcessException;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.fabric.agent.FabricAgent;
import com.liferay.portal.fabric.netty.agent.NettyFabricAgentStub;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;
import com.liferay.portal.fabric.netty.rpc.ChannelThreadLocal;
import com.liferay.portal.fabric.netty.rpc.RPCUtil;
import com.liferay.portal.fabric.netty.rpc.SyncProcessRPCCallable;
import com.liferay.portal.fabric.netty.util.NettyUtil;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerConfig;
import com.liferay.portal.fabric.netty.worker.NettyFabricWorkerStub;
import com.liferay.portal.fabric.repository.Repository;
import com.liferay.portal.fabric.worker.FabricWorker;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.GenericFutureListener;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class NettyFabricWorkerExecutionChannelHandler
	extends SimpleChannelInboundHandler<NettyFabricWorkerConfig<Serializable>> {

	public NettyFabricWorkerExecutionChannelHandler(
		Repository<Channel> repository, FabricAgent fabricAgent,
		long executionTimeout) {

		if (repository == null) {
			throw new NullPointerException("Repository is null");
		}

		if (fabricAgent == null) {
			throw new NullPointerException("Fabric agent is null");
		}

		_repository = repository;
		_fabricAgent = fabricAgent;
		_executionTimeout = executionTimeout;
	}

	@Override
	public void exceptionCaught(
		ChannelHandlerContext channelHandlerContext, Throwable throwable) {

		final Channel channel = channelHandlerContext.channel();

		_log.error("Closing " + channel + " due to:", throwable);

		ChannelFuture channelFuture = channel.close();

		channelFuture.addListener(
			new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture channelFuture) {
					if (_log.isInfoEnabled()) {
						_log.info(channel + " is closed");
					}
				}

			});
	}

	@Override
	protected void channelRead0(
		ChannelHandlerContext channelHandlerContext,
		NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

		NoticeableFuture<LoadedPaths> noticeableFuture = loadPaths(
			channelHandlerContext.channel(), nettyFabricWorkerConfig);

		noticeableFuture.addFutureListener(
			new PostLoadPathsFutureListener(
				channelHandlerContext, nettyFabricWorkerConfig));
	}

	protected NoticeableFuture<LoadedPaths> loadPaths(
		Channel channel,
		NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

		Map<Path, Path> mergedPaths = new HashMap<>();

		ProcessConfig processConfig =
			nettyFabricWorkerConfig.getProcessConfig();

		final Map<Path, Path> bootstrapPaths = new LinkedHashMap<>();

		for (PathHolder pathHolder :
				processConfig.getBootstrapClassPathHolders()) {

			bootstrapPaths.put(Paths.get(pathHolder.toString()), null);
		}

		mergedPaths.putAll(bootstrapPaths);

		final Map<Path, Path> runtimePaths = new LinkedHashMap<>();

		for (PathHolder pathHolder :
				processConfig.getRuntimeClassPathHolders()) {

			runtimePaths.put(Paths.get(pathHolder.toString()), null);
		}

		mergedPaths.putAll(runtimePaths);

		final Map<Path, Path> inputPaths =
			nettyFabricWorkerConfig.getInputPathMap();

		mergedPaths.putAll(inputPaths);

		return new NoticeableFutureConverter<LoadedPaths, Map<Path, Path>>(
			_repository.getFiles(channel, mergedPaths, false)) {

			@Override
			protected LoadedPaths convert(Map<Path, Path> mergedPaths)
				throws IOException {

				Map<Path, Path> loadedInputPaths = new HashMap<>();

				List<Path> missedInputPaths = new ArrayList<>();

				for (Path path : inputPaths.keySet()) {
					Path loadedInputPath = mergedPaths.get(path);

					if (loadedInputPath == null) {
						missedInputPaths.add(path);
					}
					else {
						loadedInputPaths.put(path, loadedInputPath);
					}
				}

				if (!missedInputPaths.isEmpty()) {
					throw new IOException(
						"Unable to get input paths: " + missedInputPaths);
				}

				List<Path> loadedBootstrapPaths = new ArrayList<>();

				List<Path> missedBootstrapPaths = new ArrayList<>();

				for (Path path : bootstrapPaths.keySet()) {
					Path loadedBootstrapPath = mergedPaths.get(path);

					if (loadedBootstrapPath == null) {
						missedBootstrapPaths.add(path);
					}
					else {
						loadedBootstrapPaths.add(loadedBootstrapPath);
					}
				}

				if (!missedBootstrapPaths.isEmpty() && _log.isWarnEnabled()) {
					_log.warn(
						"Incomplete bootstrap classpath loaded, missed: " +
							missedBootstrapPaths);
				}

				List<Path> loadedRuntimePaths = new ArrayList<>();

				List<Path> missedRuntimePaths = new ArrayList<>();

				for (Path path : runtimePaths.keySet()) {
					Path loadedRuntimePath = mergedPaths.get(path);

					if (loadedRuntimePath == null) {
						missedRuntimePaths.add(path);
					}
					else {
						loadedRuntimePaths.add(loadedRuntimePath);
					}
				}

				if (!missedRuntimePaths.isEmpty() && _log.isWarnEnabled()) {
					_log.warn(
						"Incomplete runtime classpath loaded, missed: " +
							missedRuntimePaths);
				}

				return new LoadedPaths(
					loadedInputPaths,
					StringUtil.merge(loadedBootstrapPaths, File.pathSeparator),
					StringUtil.merge(loadedRuntimePaths, File.pathSeparator));
			}

		};
	}

	protected void sendResult(
		Channel channel, long fabricWorkerId, Serializable result,
		Throwable t) {

		final FabricWorkerResultProcessCallable
			fabricWorkerResultProcessCallable =
				new FabricWorkerResultProcessCallable(
					fabricWorkerId, result, t);

		NoticeableFuture<Serializable> noticeableFuture = RPCUtil.execute(
			channel,
			new SyncProcessRPCCallable<Serializable>(
				fabricWorkerResultProcessCallable));

		NettyUtil.scheduleCancellation(
			channel, noticeableFuture, _executionTimeout);

		noticeableFuture.addFutureListener(
			new BaseFutureListener<Serializable>() {

				@Override
				public void completeWithException(
					Future<Serializable> future, Throwable throwable) {

					_log.error(
						"Unable to send back fabric worker result " +
							fabricWorkerResultProcessCallable,
						throwable);
				}

			});
	}

	protected static class FabricAgentFinishStartupProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			Channel channel = ChannelThreadLocal.getChannel();

			NettyFabricAgentStub nettyStubFabricAgent =
				NettyChannelAttributes.getNettyFabricAgentStub(channel);

			if (nettyStubFabricAgent == null) {
				throw new ProcessException(
					"Unable to locate fabric agent on channel " + channel);
			}

			nettyStubFabricAgent.finishStartup(_id);

			return null;
		}

		protected FabricAgentFinishStartupProcessCallable(long id) {
			_id = id;
		}

		private static final long serialVersionUID = 1L;

		private final long _id;

	}

	protected static class FabricWorkerResultProcessCallable
		implements ProcessCallable<Serializable> {

		@Override
		public Serializable call() throws ProcessException {
			Channel channel = ChannelThreadLocal.getChannel();

			NettyFabricAgentStub nettyStubFabricAgent =
				NettyChannelAttributes.getNettyFabricAgentStub(channel);

			if (nettyStubFabricAgent == null) {
				throw new ProcessException(
					"Unable to locate fabric agent on channel " + channel);
			}

			NettyFabricWorkerStub<Serializable> nettyStubFabricWorker =
				(NettyFabricWorkerStub<Serializable>)
					nettyStubFabricAgent.takeNettyStubFabricWorker(_id);

			if (nettyStubFabricWorker == null) {
				throw new ProcessException(
					StringBundler.concat(
						"Unable to locate fabric worker on channel ", channel,
						", with fabric worker id ", _id));
			}

			if (_throwable != null) {
				nettyStubFabricWorker.setException(_throwable);
			}
			else {
				nettyStubFabricWorker.setResult(_result);
			}

			return null;
		}

		@Override
		public String toString() {
			StringBundler sb = new StringBundler(7);

			sb.append("{id=");
			sb.append(_id);
			sb.append(", result=");
			sb.append(_result);
			sb.append(", throwable=");
			sb.append(_throwable);
			sb.append("}");

			return sb.toString();
		}

		protected FabricWorkerResultProcessCallable(
			long id, Serializable result, Throwable throwable) {

			_id = id;
			_result = result;
			_throwable = throwable;
		}

		private static final long serialVersionUID = 1L;

		private final long _id;
		private final Serializable _result;
		private final Throwable _throwable;

	}

	protected static class LoadedPaths {

		public LoadedPaths(
			Map<Path, Path> inputPaths, String bootstrapClassPath,
			String runtimeClassPath) {

			_inputPaths = inputPaths;
			_bootstrapClassPath = bootstrapClassPath;
			_runtimeClassPath = runtimeClassPath;
		}

		public Map<Path, Path> getInputPaths() {
			return _inputPaths;
		}

		public ProcessConfig toProcessConfig(ProcessConfig processConfig)
			throws ProcessException {

			ProcessConfig.Builder builder = new ProcessConfig.Builder();

			builder.setArguments(processConfig.getArguments());
			builder.setBootstrapClassPath(_bootstrapClassPath);
			builder.setJavaExecutable(processConfig.getJavaExecutable());
			builder.setRuntimeClassPath(_runtimeClassPath);

			try {
				List<URL> urls = new ArrayList<>();

				Collections.addAll(
					urls, ClassPathUtil.getClassPathURLs(_bootstrapClassPath));

				Collections.addAll(
					urls, ClassPathUtil.getClassPathURLs(_runtimeClassPath));

				builder.setReactClassLoader(
					new URLClassLoader(urls.toArray(new URL[0])));
			}
			catch (MalformedURLException murle) {
				throw new ProcessException(murle);
			}

			return builder.build();
		}

		private final String _bootstrapClassPath;
		private final Map<Path, Path> _inputPaths;
		private final String _runtimeClassPath;

	}

	protected class PostFabricWorkerExecutionFutureListener
		implements GenericFutureListener
			<io.netty.util.concurrent.Future<FabricWorker<Serializable>>> {

		public PostFabricWorkerExecutionFutureListener(
			Channel channel, LoadedPaths loadedPaths,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

			_channel = channel;
			_loadedPaths = loadedPaths;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
		}

		@Override
		public void operationComplete(
				io.netty.util.concurrent.Future<FabricWorker<Serializable>>
					future)
			throws Exception {

			Throwable throwable = future.cause();

			if (throwable != null) {
				sendResult(
					_channel, _nettyFabricWorkerConfig.getId(), null,
					throwable);

				return;
			}

			FabricWorker<Serializable> fabricWorker = future.get();

			NettyChannelAttributes.putFabricWorker(
				_channel, _nettyFabricWorkerConfig.getId(), fabricWorker);

			NoticeableFuture<Serializable> noticeableFuture = RPCUtil.execute(
				_channel,
				new SyncProcessRPCCallable<Serializable>(
					new FabricAgentFinishStartupProcessCallable(
						_nettyFabricWorkerConfig.getId())));

			NettyUtil.scheduleCancellation(
				_channel, noticeableFuture, _executionTimeout);

			noticeableFuture.addFutureListener(
				new BaseFutureListener<Serializable>() {

					@Override
					public void completeWithException(
						Future<Serializable> future, Throwable throwable) {

						_log.error(
							"Unable to finish fabric worker startup",
							throwable);
					}

				});

			NoticeableFuture<Serializable> processNoticeableFuture =
				fabricWorker.getProcessNoticeableFuture();

			processNoticeableFuture.addFutureListener(
				new PostFabricWorkerFinishFutureListener(
					_channel, _nettyFabricWorkerConfig, _loadedPaths));
		}

		private final Channel _channel;
		private final LoadedPaths _loadedPaths;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;

	}

	protected class PostFabricWorkerFinishFutureListener
		implements FutureListener<Serializable> {

		public PostFabricWorkerFinishFutureListener(
			Channel channel,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig,
			LoadedPaths loadedPaths) {

			_channel = channel;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
			_loadedPaths = loadedPaths;
		}

		@Override
		public void complete(Future<Serializable> future) {
			Map<Path, Path> inputPaths = _loadedPaths.getInputPaths();

			for (Path path : inputPaths.values()) {
				FileHelperUtil.delete(true, path);
			}

			try {
				sendResult(
					_channel, _nettyFabricWorkerConfig.getId(), future.get(),
					null);
			}
			catch (Throwable t) {
				if (t instanceof ExecutionException) {
					t = t.getCause();
				}

				sendResult(_channel, _nettyFabricWorkerConfig.getId(), null, t);
			}
		}

		private final Channel _channel;
		private final LoadedPaths _loadedPaths;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;

	}

	protected class PostLoadPathsFutureListener
		extends BaseFutureListener<LoadedPaths> {

		public PostLoadPathsFutureListener(
			ChannelHandlerContext channelHandlerContext,
			NettyFabricWorkerConfig<Serializable> nettyFabricWorkerConfig) {

			_channelHandlerContext = channelHandlerContext;
			_nettyFabricWorkerConfig = nettyFabricWorkerConfig;
		}

		@Override
		public void completeWithException(
			Future<LoadedPaths> future, Throwable throwable) {

			sendResult(
				_channelHandlerContext.channel(),
				_nettyFabricWorkerConfig.getId(), null, throwable);
		}

		@Override
		public void completeWithResult(
			Future<LoadedPaths> loadPathsFuture,
			final LoadedPaths loadedPaths) {

			EventExecutor eventExecutor = _channelHandlerContext.executor();

			io.netty.util.concurrent.Future<FabricWorker<Serializable>> future =
				eventExecutor.submit(
					new Callable<FabricWorker<Serializable>>() {

						@Override
						public FabricWorker<Serializable> call()
							throws ProcessException {

							return _fabricAgent.execute(
								loadedPaths.toProcessConfig(
									_nettyFabricWorkerConfig.
										getProcessConfig()),
								_nettyFabricWorkerConfig.getProcessCallable());
						}

					});

			future.addListener(
				new PostFabricWorkerExecutionFutureListener(
					_channelHandlerContext.channel(), loadedPaths,
					_nettyFabricWorkerConfig));
		}

		private final ChannelHandlerContext _channelHandlerContext;
		private final NettyFabricWorkerConfig<Serializable>
			_nettyFabricWorkerConfig;

	}

	private static final Log _log = LogFactoryUtil.getLog(
		NettyFabricWorkerExecutionChannelHandler.class);

	private final long _executionTimeout;
	private final FabricAgent _fabricAgent;
	private final Repository<Channel> _repository;

}