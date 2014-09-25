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

package com.liferay.portal.nio.intraband.proxy;

import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeleton;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeletonRegistryUtil;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.ClassLoaderPool;

import java.lang.reflect.Constructor;

import java.util.Arrays;
import java.util.concurrent.Future;

/**
 * @author Shuyang Zhou
 */
public class IntrabandProxyInstallationUtil {

	public static void checkProxyMethodSignatures(
		String[] skeletonProxyMethodSignatures,
		String[] stubProxyMethodSignatures) {

		if (Arrays.equals(
				skeletonProxyMethodSignatures, stubProxyMethodSignatures)) {

			return;
		}

		String skeletonProxyMethodSignaturesString = Arrays.toString(
			skeletonProxyMethodSignatures);
		String stubProxyMethodSignaturesString = Arrays.toString(
			stubProxyMethodSignatures);

		throw new IllegalStateException(
			"Skeleton and stub proxy method signatures do not match. " +
				"Skeleton is " + skeletonProxyMethodSignaturesString +
					". Stub is " + stubProxyMethodSignaturesString + ".");
	}

	public static String[] installSkeleton(
		Class<?> clazz, TargetLocator targetLocator) {

		return installSkeleton(clazz.getClassLoader(), clazz, targetLocator);
	}

	public static String[] installSkeleton(
		ClassLoader classLoader, Class<?> clazz, TargetLocator targetLocator) {

		try {
			Class<?> proxySkeletonClass = IntrabandProxyUtil.getSkeletonClass(
				classLoader, clazz);

			Constructor<? extends IntrabandProxySkeleton> constructor =
				(Constructor<? extends IntrabandProxySkeleton>)
					proxySkeletonClass.getConstructor(TargetLocator.class);

			IntrabandProxySkeletonRegistryUtil.register(
				clazz.getName(), constructor.newInstance(targetLocator));

			return IntrabandProxyUtil.getProxyMethodSignatures(
				proxySkeletonClass);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Future<String[]> installSkeleton(
		RegistrationReference registrationReference, Class<?> clazz,
		TargetLocator targetLocator) {

		return installSkeleton(
			registrationReference, clazz.getClassLoader(), clazz,
			targetLocator);
	}

	public static Future<String[]> installSkeleton(
		RegistrationReference registrationReference, ClassLoader classLoader,
		Class<?> clazz, TargetLocator targetLocator) {

		return IntrabandRPCUtil.execute(
			registrationReference,
			new InstallSkeletonProcessCallable(
				classLoader, clazz, targetLocator));
	}

	protected static class InstallSkeletonProcessCallable
		implements ProcessCallable<String[]> {

		@Override
		public String[] call() throws ProcessException {
			ClassLoader classLoader = ClassLoaderPool.getClassLoader(
				_servletContextName);

			try {
				return installSkeleton(
					classLoader, classLoader.loadClass(_skeletonId),
					_targetLocator);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}
		}

		protected InstallSkeletonProcessCallable(
			ClassLoader classLoader, Class<?> clazz,
			TargetLocator targetLocator) {

			_servletContextName = ClassLoaderPool.getContextName(classLoader);
			_skeletonId = clazz.getName();
			_targetLocator = targetLocator;
		}

		private static final long serialVersionUID = 1L;

		private final String _servletContextName;
		private final String _skeletonId;
		private final TargetLocator _targetLocator;

	}

}