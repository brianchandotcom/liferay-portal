/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.launch.set.publisher;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.List;

/**
 * @author Alberto Moreno
 */
public class LaunchSetPublishException extends PortalException {

	public List<Object> getArguments() {
		return _arguments;
	}

	public String getMessageKey() {
		return _messageKey;
	}

	public static class MustHaveLaunchEntries
		extends LaunchSetPublishException {

		public MustHaveLaunchEntries(long launchSetId, String launchSetName) {
			super(
				launchSetName,
				"Launch set " + launchSetId + " has no launch entries",
				"the-launch-x-must-contain-at-least-one-asset");
		}

	}

	public static class MustNotBePublished extends LaunchSetPublishException {

		public MustNotBePublished(long launchSetId, String launchSetName) {
			super(
				launchSetName,
				"Launch set " + launchSetId + " was already published",
				"the-launch-x-was-already-published");
		}

	}

	public static class MustNotBePublishing extends LaunchSetPublishException {

		public MustNotBePublishing(long launchSetId, String launchSetName) {
			super(
				launchSetName,
				"Launch set " + launchSetId + " is already being published",
				"the-launch-x-is-already-being-published");
		}

	}

	public static class MustNotBeScheduled extends LaunchSetPublishException {

		public MustNotBeScheduled(long launchSetId, String launchSetName) {
			super(
				launchSetName,
				"Launch set " + launchSetId + " is scheduled to be published",
				"the-launch-x-is-scheduled-to-be-published");
		}

	}

	private LaunchSetPublishException(
		String launchSetName, String message, String messageKey) {

		super(message);

		_arguments = Collections.singletonList(launchSetName);

		_messageKey = messageKey;
	}

	private final List<Object> _arguments;
	private final String _messageKey;

}