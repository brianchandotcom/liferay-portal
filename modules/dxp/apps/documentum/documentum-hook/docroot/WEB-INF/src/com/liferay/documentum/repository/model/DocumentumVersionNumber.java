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