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

package com.liferay.sync.engine.lan.server.file;

import com.liferay.sync.engine.lan.util.LanClientUtil;
import com.liferay.sync.engine.lan.util.LanPEMParserUtil;
import com.liferay.sync.engine.model.SyncAccount;
import com.liferay.sync.engine.service.SyncAccountService;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SniHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslProvider;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.DomainNameMapping;
import io.netty.util.DomainNameMappingBuilder;

import java.security.cert.X509Certificate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dennis Ju
 */
public class LanFileServerInitializer
	extends ChannelInitializer<SocketChannel> {

	public LanFileServerInitializer(
		SyncTrafficShapingHandler syncTrafficShapingHandler) {

		_syncTrafficShapingHandler = syncTrafficShapingHandler;

		updateDomainNameMapping();
	}

	@Override
	public void initChannel(SocketChannel socketChannel) {
		ChannelPipeline channelPipeline = socketChannel.pipeline();

		if (_domainNameMapping != null) {
			channelPipeline.addLast(new SniHandler(_domainNameMapping));
		}

		channelPipeline.addLast(new HttpServerCodec());
		channelPipeline.addLast(new HttpObjectAggregator(65536));
		channelPipeline.addLast(_syncTrafficShapingHandler);
		channelPipeline.addLast(new ChunkedWriteHandler());
		channelPipeline.addLast(
			new LanFileServerHandler(_syncTrafficShapingHandler));
	}

	public void updateDomainNameMapping() {
		DomainNameMappingBuilder<SslContext> domainNameMappingBuilder = null;

		for (SyncAccount syncAccount : SyncAccountService.findAll()) {
			if (!syncAccount.isActive() || !syncAccount.isLanEnabled()) {
				continue;
			}

			SslContext sslContext = null;

			try {
				X509Certificate x509Certificate =
					LanPEMParserUtil.parseX509Certificate(
						syncAccount.getLanCertificate());

				SslContextBuilder sslContextBuilder =
					SslContextBuilder.forServer(
						LanPEMParserUtil.parsePrivateKey(
							syncAccount.getLanKey()),
						x509Certificate);

				sslContextBuilder.clientAuth(ClientAuth.REQUIRE);
				sslContextBuilder.sslProvider(SslProvider.JDK);
				sslContextBuilder.trustManager(x509Certificate);

				sslContext = sslContextBuilder.build();
			}
			catch (Exception e) {
				_logger.error(e.getMessage(), e);

				continue;
			}

			if (domainNameMappingBuilder == null) {
				domainNameMappingBuilder = new DomainNameMappingBuilder<>(
					sslContext);
			}

			domainNameMappingBuilder.add(
				LanClientUtil.getSNIHostname(syncAccount.getLanServerUuid()),
				sslContext);
		}

		if (domainNameMappingBuilder == null) {
			return;
		}

		_domainNameMapping = domainNameMappingBuilder.build();
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LanFileServerInitializer.class);

	private DomainNameMapping<SslContext> _domainNameMapping;
	private final SyncTrafficShapingHandler _syncTrafficShapingHandler;

}