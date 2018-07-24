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

package com.liferay.documentum.repository.model;

/**
 * @author Iván Zaera
 */
public class DocumentumVersionNumber
	implements Comparable<DocumentumVersionNumber> {

	public DocumentumVersionNumber(int major, int minor) {
		this.major = major;
		this.minor = minor;
	}

	public DocumentumVersionNumber(String versionLabel) {
		String[] parts = versionLabel.split("\\.");

		major = Integer.valueOf(parts[0]);
		minor = Integer.valueOf(parts[1]);
	}

	@Override
	public int compareTo(DocumentumVersionNumber documentumVersionNumber) {
		if (major != documentumVersionNumber.major) {
			return major - documentumVersionNumber.major;
		}

		return minor - documentumVersionNumber.minor;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DocumentumVersionNumber)) {
			return false;
		}

		DocumentumVersionNumber documentumVersionNumber =
			(DocumentumVersionNumber)obj;

		if ((major == documentumVersionNumber.major) &&
			(minor == documentumVersionNumber.minor)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int result = major;

		result = 31 * result + minor;

		return result;
	}

	public DocumentumVersionNumber increment(boolean incrementMajor) {
		if (incrementMajor) {
			return new DocumentumVersionNumber(major + 1, 0);
		}

		return new DocumentumVersionNumber(major, minor + 1);
	}

	@Override
	public String toString() {
		return major + "." + minor;
	}

	public final int major;
	public final int minor;

}