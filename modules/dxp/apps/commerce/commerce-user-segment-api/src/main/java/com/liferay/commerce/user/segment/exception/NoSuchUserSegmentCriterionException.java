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

package com.liferay.commerce.user.segment.exception;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
@ProviderType
public class NoSuchUserSegmentCriterionException extends NoSuchModelException {

	public NoSuchUserSegmentCriterionException() {
	}

	public NoSuchUserSegmentCriterionException(String msg) {
		super(msg);
	}

	public NoSuchUserSegmentCriterionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoSuchUserSegmentCriterionException(Throwable cause) {
		super(cause);
	}

}