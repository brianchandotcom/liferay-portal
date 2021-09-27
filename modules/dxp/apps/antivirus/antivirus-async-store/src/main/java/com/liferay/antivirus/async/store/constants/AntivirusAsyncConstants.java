/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.antivirus.async.store.constants;

/**
 * @author Raymond Augé
 */
public class AntivirusAsyncConstants {

	public static final String ANTIVIRUS = "antivirus";

	public static final String ANTIVIRUS_DESTINATION =
		"liferay/" + ANTIVIRUS + "_destination";

	public static final String ANTIVIRUS_GROUP_NAME = "Antivirus Scan";

	public static final String KEY_CAUTION = "caution[antivirus]";

	public static final String KEY_ERROR = "error[antivirus]";

	public static final String KEY_PROCESSING_ERROR =
		"x-was-subject-to-a-processing-error.-another-attempt-will-be-made-" +
			"in-x-minutes";

	public static final String KEY_SIZE_EXCEEDED =
		"x-could-not-be-scanned-because-it-exceeds-the-size-limit-allowed-by-" +
			"the-antivirus-service";

	public static final String KEY_VIRUS_FOUND =
		"x-was-found-to-contain-a-virus-x.-its-contents-have-been-" +
			"quarantined.-please-contact-the-system-administrator";

	public static final String KEY_WARNING = "warning[antivirus]";

	public static final long QUARANTINE_REPOSITORY_ID = 999999999999999999L;

}
