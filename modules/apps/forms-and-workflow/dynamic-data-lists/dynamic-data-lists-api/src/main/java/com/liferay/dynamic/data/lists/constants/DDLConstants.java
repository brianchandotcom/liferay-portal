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

package com.liferay.dynamic.data.lists.constants;

/**
 * Represents a class that hold generic constants used in DDL services.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class DDLConstants {

	/**
	 * {@value #RESERVED_DDM_STRUCTURE_ID} Reserved String value that holds the value of the Structure ID configured for the Record Set. This value is available DDL Display Template context as injected variable value.
	 */
	public static final String RESERVED_DDM_STRUCTURE_ID =
		"reserved_ddm_structure_id";

	/**
	 * {@value #RESERVED_DDM_TEMPLATE_ID} Reserved String value that holds the value of the current Template ID. This value is available DDL Display Template context as injected variable value.
	 */
	public static final String RESERVED_DDM_TEMPLATE_ID =
		"reserved_ddm_template_id";

	/**
	 * {@value #RESERVED_RECORD_SET_DESCRIPTION} Reserved String value that holds the localized value of the Record Set description. This value is available DDL Display Template context as injected variable value.
	 */
	public static final String RESERVED_RECORD_SET_DESCRIPTION =
		"reserved_record_set_description";

	/**
	 * {@value #RESERVED_RECORD_SET_ID} Reserved String value that holds the value of the Record Set ID. This value is available DDL Display Template context as injected variable value.
	 */
	public static final String RESERVED_RECORD_SET_ID =
		"reserved_record_set_id";

	/**
	 * {@value #RESERVED_RECORD_SET_NAME} Reserved String value that holds the localized value of the Record Set name. This value is available DDL Display Template context as injected variable value.
	 */
	public static final String RESERVED_RECORD_SET_NAME =
		"reserved_record_set_name";

}