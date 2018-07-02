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
 * Holds generic constants used in DDL services.
 *
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
public class DDLConstants {

	/**
	 * {@value #RESERVED_DDM_STRUCTURE_ID} is a Reserved String and the name of
	 * the DDL Display Template variable that holds the Structure ID configured
	 * for the Record Set. The variable is injected into the DDL Display
	 * Template context.
	 */
	public static final String RESERVED_DDM_STRUCTURE_ID =
		"reserved_ddm_structure_id";

	/**
	 * {@value #RESERVED_DDM_TEMPLATE_ID} is a Reserved String and the name of
	 * the DDL Display Template variable that holds the Template ID. The
	 * variable is injected into the DDL Display Template context.
	 */
	public static final String RESERVED_DDM_TEMPLATE_ID =
		"reserved_ddm_template_id";

	/**
	 * {@value #RESERVED_RECORD_SET_DESCRIPTION} is a Reserved String and the
	 * name of the DDL Display Template variable that holds the Record Set
	 * description. The variable is injected into the DDL Display Template
	 * context.
	 */
	public static final String RESERVED_RECORD_SET_DESCRIPTION =
		"reserved_record_set_description";

	/**
	 * {@value #RESERVED_RECORD_SET_ID} is a Reserved String and the name of the
	 * DDL Display Template variable that holds the Record Set ID. The variable
	 * is injected into the DDL Display Template context.
	 */
	public static final String RESERVED_RECORD_SET_ID =
		"reserved_record_set_id";

	/**
	 * {@value #RESERVED_RECORD_SET_NAME} is a Reserved String and the name of
	 * the DDL Display Template variable that holds the Record Set name. The
	 * variable is injected into the DDL Display Template context.
	 */
	public static final String RESERVED_RECORD_SET_NAME =
		"reserved_record_set_name";

	public static final String RESOURCE_NAME = "com.liferay.dynamic.data.lists";

	/**
	 * @deprecated As of Wilberforce (7.0.x), with no direct replacement
	 */
	@Deprecated
	public static final String SERVICE_NAME = "com.liferay.dynamic.data.lists";

}