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

package com.liferay.portal.kernel.test;

import com.liferay.petra.memory.FinalizeAction;
import com.liferay.petra.memory.FinalizeManager;
import com.liferay.petra.reflect.ReflectionUtil;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Constructor;

import java.util.Map;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManagerUtil {

	public static void drainPendingFinalizeActions() {
		ReferenceQueue<Object> referenceQueue =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_referenceQueue");

		Map<?, FinalizeAction> finalizeActions =
			ReflectionTestUtil.getFieldValue(
				FinalizeManager.class, "_finalizeActions");

		Reference<? extends Object> reference = null;

		while ((reference = referenceQueue.poll()) != null) {
			try {
				FinalizeAction finalizeAction = finalizeActions.remove(
					_identityKeyConstructor.newInstance(reference));

				if (finalizeAction != null) {
					try {
						finalizeAction.doFinalize(reference);
					}
					finally {
						reference.clear();
					}
				}
			}
			catch (ReflectiveOperationException roe) {
				ReflectionUtil.throwException(roe);
			}
		}
	}

	private static final Constructor<?> _identityKeyConstructor;

	static {
		try {
			Class<?> identityKeyClass = Class.forName(
				FinalizeManager.class.getName() + "$IdentityKey");

			_identityKeyConstructor = identityKeyClass.getDeclaredConstructor(
				Reference.class);

			_identityKeyConstructor.setAccessible(true);
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}
	}

}