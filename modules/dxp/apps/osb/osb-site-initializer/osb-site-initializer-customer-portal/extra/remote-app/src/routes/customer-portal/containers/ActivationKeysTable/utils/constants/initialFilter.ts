/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const INITIAL_FILTER = {
	environmentTypes: {
		name: 'Environment Type',
		value: [],
	},
	expirationDate: {
		name: 'Exp. Date',
		value: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
	},
	hasValue: false,
	instanceSizes: {
		name: 'Instance Size',
		value: [],
	},
	keyType: {
		name: 'Key Type',
		value: {
			hasCluster: undefined,
			hasOnPremise: undefined,
			hasVirtualCluster: undefined,
			maxNodes: '',
			minNodes: '',
		},
	},
	productVersions: {
		name: 'Product Version',
		value: [],
	},
	searchTerm: '',
	startDate: {
		name: 'Start Date',
		value: {
			onOrAfter: undefined,
			onOrBefore: undefined,
		},
	},
	status: {
		name: 'Status',
		value: [],
	},
};
