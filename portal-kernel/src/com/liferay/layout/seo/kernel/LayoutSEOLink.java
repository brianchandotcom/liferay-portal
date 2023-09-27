/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.kernel;

/**
 * @author Cristina González
 */
public interface LayoutSEOLink {

	public String getHref();

	public String getHrefLang();

	public Relationship getRelationship();

	public enum Relationship {

		ALTERNATE {

			public String toString() {
				return "alternate";
			}

		},
		CANONICAL {

			public String toString() {
				return "canonical";
			}

		}

	}

}