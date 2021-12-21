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
 */

const BUFFER_ATTR = [null, '="', null, '" '];
const BUFFER_CLOSE_NODE = ['</', null, '>'];
const BUFFER_OPEN_NODE = ['<', null, null, '>'];
const COL_TYPES_ASSIGNMENT = [
	'address',
	'receptionType',
	'resourceActions',
	'roleId',
	'roleType',
	'scriptedAssignment',
	'scriptedRecipient',
	'taskAssignees',
	'user',
	'userId',
];
const COL_TYPES_FIELD = [
	'condition',
	'fork',
	'join',
	'join-xor',
	'state',
	'task',
];
const DEFAULT_LANGUAGE = 'groovy';
const STR_BLANK = '';
const STR_CDATA_CLOSE = ']]>';
const STR_CDATA_OPEN = '<![CDATA[';
const STR_CHAR_CRLF = '\r\n';
const STR_CHAR_CR_LF_CRLF = /\r\n|\r|\n/;
const STR_CHAR_TAB = '\t';
const STR_DASH = '-';
const STR_METADATA = '<metadata';
const STR_SPACE = ' ';

const xmlNamespace = {
	'xmlns': 'urn:liferay.com:liferay-workflow_7.4.0',
	'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
	'xsi:schemaLocation':
		'urn:liferay.com:liferay-workflow_7.4.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_4_0.xsd',
};

export {
	BUFFER_ATTR,
	BUFFER_CLOSE_NODE,
	BUFFER_OPEN_NODE,
	COL_TYPES_ASSIGNMENT,
	COL_TYPES_FIELD,
	DEFAULT_LANGUAGE,
	STR_BLANK,
	STR_CDATA_CLOSE,
	STR_CDATA_OPEN,
	STR_CHAR_CRLF,
	STR_CHAR_CR_LF_CRLF,
	STR_CHAR_TAB,
	STR_DASH,
	STR_METADATA,
	STR_SPACE,
	xmlNamespace,
};
