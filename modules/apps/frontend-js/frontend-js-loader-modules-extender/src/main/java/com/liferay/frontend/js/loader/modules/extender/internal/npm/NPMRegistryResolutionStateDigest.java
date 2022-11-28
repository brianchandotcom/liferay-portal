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

package com.liferay.frontend.js.loader.modules.extender.internal.npm;

import com.liferay.frontend.js.loader.modules.extender.npm.JSModule;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackage;
import com.liferay.frontend.js.loader.modules.extender.npm.JSPackageDependency;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Computes a digest (hash) with ALL values that can make module resolutions
 * change.
 *
 * The data added to the digest is based on package names, versions,
 * dependencies and any other information that, when changed, may alter the way
 * modules are resolved.
 *
 * The digest is stable throughout different cluster nodes so that if two nodes
 * share the same state, their digests are identical.
 *
 * @author Iván Zaera Avellón
 * @review
 */
public class NPMRegistryResolutionStateDigest {

	public NPMRegistryResolutionStateDigest(NPMRegistry npmRegistry) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");

			for (JSModule jsModule : npmRegistry.getResolvedJSModules()) {
				_digest(messageDigest, jsModule);
			}

			_digest = StringUtil.bytesToHexString(messageDigest.digest());
		}
		catch (NoSuchAlgorithmException noSuchAlgorithmException) {
			throw new RuntimeException(noSuchAlgorithmException);
		}
	}

	public String getDigest() {
		return _digest;
	}

	private void _digest(MessageDigest messageDigest, JSModule jsModule) {
		_digest(messageDigest, jsModule.getJSPackage());
		_digest(messageDigest, jsModule.getName());

		for (String dependency : jsModule.getDependencies()) {
			_digest(messageDigest, dependency);
		}
	}

	private void _digest(MessageDigest messageDigest, JSPackage jsPackage) {
		_digest(messageDigest, jsPackage.getMainModuleName());
		_digest(messageDigest, jsPackage.getName());
		_digest(messageDigest, jsPackage.getVersion());

		for (JSPackageDependency jsPackageDependency :
				jsPackage.getJSPackageDependencies()) {

			_digestState(messageDigest, jsPackageDependency);
		}
	}

	private void _digest(MessageDigest messageDigest, String string) {
		try {
			messageDigest.digest(string.getBytes(StringPool.UTF8));
		}
		catch (UnsupportedEncodingException unsupportedEncodingException) {
			throw new RuntimeException(unsupportedEncodingException);
		}
	}

	private void _digestState(
		MessageDigest messageDigest, JSPackageDependency jsPackageDependency) {

		_digest(messageDigest, jsPackageDependency.getPackageName());
		_digest(messageDigest, jsPackageDependency.getVersionConstraints());
	}

	private final String _digest;

}