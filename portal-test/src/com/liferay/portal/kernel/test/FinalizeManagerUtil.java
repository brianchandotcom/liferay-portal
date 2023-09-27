/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			catch (ReflectiveOperationException reflectiveOperationException) {
				ReflectionUtil.throwException(reflectiveOperationException);
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
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new ExceptionInInitializerError(reflectiveOperationException);
		}
	}

}