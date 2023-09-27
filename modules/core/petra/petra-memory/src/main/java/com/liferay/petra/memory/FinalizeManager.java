/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.memory;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class FinalizeManager {

	public static final ReferenceFactory PHANTOM_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityPhantomReference<T>(
					reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(new IdentityKey(this));

						super.clear();
					}

				};
			}

		};

	public static final ReferenceFactory SOFT_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualitySoftReference<T>(reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(new IdentityKey(this));

						super.clear();
					}

				};
			}

		};

	public static final ReferenceFactory WEAK_REFERENCE_FACTORY =
		new ReferenceFactory() {

			@Override
			public <T> Reference<T> createReference(
				T reference, ReferenceQueue<? super T> referenceQueue) {

				return new EqualityWeakReference<T>(reference, referenceQueue) {

					@Override
					public void clear() {
						_finalizeActions.remove(new IdentityKey(this));

						super.clear();
					}

				};
			}

		};

	public static <T> Reference<T> register(
		T reference, FinalizeAction finalizeAction,
		ReferenceFactory referenceFactory) {

		Reference<T> newReference = referenceFactory.createReference(
			reference, _referenceQueue);

		_finalizeActions.put(new IdentityKey(newReference), finalizeAction);

		return newReference;
	}

	public interface ReferenceFactory {

		public <T> Reference<T> createReference(
			T realReference, ReferenceQueue<? super T> referenceQueue);

	}

	private static final Map<IdentityKey, FinalizeAction> _finalizeActions =
		new ConcurrentHashMap<>();
	private static final ReferenceQueue<Object> _referenceQueue =
		new ReferenceQueue<>();

	private static class FinalizeThread extends Thread {

		@Override
		public void run() {
			while (true) {
				try {
					Reference<? extends Object> reference =
						_referenceQueue.remove();

					FinalizeAction finalizeAction = _finalizeActions.remove(
						new IdentityKey(reference));

					if (finalizeAction != null) {
						try {
							finalizeAction.doFinalize(reference);
						}
						finally {
							reference.clear();
						}
					}
				}
				catch (InterruptedException interruptedException) {
				}
			}
		}

		private FinalizeThread(String name) {
			super(name);
		}

	}

	private static class IdentityKey {

		@Override
		public boolean equals(Object object) {
			IdentityKey identityKey = (IdentityKey)object;

			if (_reference == identityKey._reference) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return _reference.hashCode();
		}

		private IdentityKey(Reference<?> reference) {
			_reference = reference;
		}

		private final Reference<?> _reference;

	}

	static {
		Thread thread = new FinalizeThread("Finalize Thread");

		thread.setContextClassLoader(null);
		thread.setDaemon(true);

		thread.start();
	}

}