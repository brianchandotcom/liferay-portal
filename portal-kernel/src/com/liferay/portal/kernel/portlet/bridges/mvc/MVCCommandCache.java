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

package com.liferay.portal.kernel.portlet.bridges.mvc;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.util.StringPlus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Sergio González
 */
public class MVCCommandCache {

	public MVCCommandCache(
		MVCCommand emptyMVCCommand, String packagePrefix, String portletName,
		Class<? extends MVCCommand> mvcCommandClass, String mvcCommandPostFix) {

		_emptyMVCCommand = emptyMVCCommand;
		_mvcComandPostFix = mvcCommandPostFix;

		if (Validator.isNotNull(packagePrefix) &&
			!packagePrefix.endsWith(StringPool.PERIOD)) {

			packagePrefix = packagePrefix + StringPool.PERIOD;
		}

		_packagePrefix = packagePrefix;

		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			mvcCommandClass,
			"(&(javax.portlet.name=" + portletName + ")(mvc.command.name=*))",
			new ServiceReferenceMapper<String, MVCCommand>() {

				@Override
				public void map(
					ServiceReference<MVCCommand> serviceReference,
					Emitter<String> emitter) {

					List<String> mvcCommandNames = StringPlus.asList(
						serviceReference.getProperty("mvc.command.name"));

					for (String mvcCommandName : mvcCommandNames) {
						emitter.emit(mvcCommandName);
					}
				}

			});
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #MVCCommandCache(MVCCommand,
	 *             String, String, Class, String)}
	 */
	@Deprecated
	public MVCCommandCache(
		MVCCommand emptyMVCCommand, String packagePrefix, String portletName,
		String mvcCommandClassName, String mvcCommandPostFix) {

		this(
			emptyMVCCommand, packagePrefix, portletName,
			_getMVCCommandClass(mvcCommandClassName), mvcCommandPostFix);
	}

	public void close() {
		_serviceTrackerMap.close();
	}

	public MVCCommand getMVCCommand(String mvcCommandName) {
		String className = null;

		MVCCommand mvcCommand = _serviceTrackerMap.getService(mvcCommandName);

		if (mvcCommand != null) {
			return mvcCommand;
		}

		try {
			mvcCommand = _mvcCommandCache.get(mvcCommandName);

			if (mvcCommand != null) {
				return mvcCommand;
			}

			if (Validator.isNull(_packagePrefix)) {
				return _emptyMVCCommand;
			}

			StringBundler sb = new StringBundler(4);

			sb.append(_packagePrefix);
			sb.append(Character.toUpperCase(mvcCommandName.charAt(0)));
			sb.append(mvcCommandName.substring(1));
			sb.append(_mvcComandPostFix);

			className = sb.toString();

			mvcCommand = (MVCCommand)InstanceFactory.newInstance(className);

			_mvcCommandCache.put(mvcCommandName, mvcCommand);

			return mvcCommand;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to instantiate MVCCommand " + className);
			}

			_mvcCommandCache.put(mvcCommandName, _emptyMVCCommand);

			return _emptyMVCCommand;
		}
	}

	public Set<String> getMVCCommandNames() {
		return _serviceTrackerMap.keySet();
	}

	public List<? extends MVCCommand> getMVCCommands(String key) {
		List<MVCCommand> mvcCommands = _mvcCommands.get(key);

		String[] mvcCommandNames = StringUtil.split(key);

		if ((mvcCommands != null) &&
			(mvcCommands.size() == mvcCommandNames.length)) {

			return mvcCommands;
		}

		mvcCommands = new ArrayList<>();

		for (String mvcCommandName : mvcCommandNames) {
			MVCCommand mvcCommand = getMVCCommand(mvcCommandName);

			if (mvcCommand != _emptyMVCCommand) {
				mvcCommands.add(mvcCommand);
			}
			else {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to find MVCCommand " + key);
				}
			}
		}

		_mvcCommands.put(key, mvcCommands);

		for (MVCCommand mvcCommand : mvcCommands) {
			String mvcCommandClassName = ClassUtil.getClassName(mvcCommand);

			List<String> keys = _mvcCommandKeys.get(mvcCommandClassName);

			if (keys == null) {
				keys = new ArrayList<>();

				_mvcCommandKeys.put(mvcCommandClassName, keys);
			}

			keys.add(key);
		}

		return mvcCommands;
	}

	public boolean isEmpty() {
		return _mvcCommandCache.isEmpty();
	}

	private static Class<? extends MVCCommand> _getMVCCommandClass(
		String mvcCommandClassName) {

		Class<? extends MVCCommand> mvcCommandClass = null;

		for (Class<? extends MVCCommand> curMVCCommandClass :
				_mvcCommandClasses) {

			if (mvcCommandClassName.equals(curMVCCommandClass.getName())) {
				mvcCommandClass = curMVCCommandClass;

				break;
			}
		}

		if (mvcCommandClass == null) {
			throw new IllegalArgumentException();
		}

		return mvcCommandClass;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MVCCommandCache.class);

	private static final List<Class<? extends MVCCommand>> _mvcCommandClasses =
		Arrays.asList(
			MVCActionCommand.class, MVCRenderCommand.class,
			MVCResourceCommand.class);

	private final MVCCommand _emptyMVCCommand;
	private final String _mvcComandPostFix;
	private final Map<String, MVCCommand> _mvcCommandCache =
		new ConcurrentHashMap<>();
	private final Map<String, List<String>> _mvcCommandKeys =
		new ConcurrentHashMap<>();
	private final Map<String, List<MVCCommand>> _mvcCommands =
		new ConcurrentHashMap<>();
	private final String _packagePrefix;
	private final ServiceTrackerMap<String, ? extends MVCCommand>
		_serviceTrackerMap;

}