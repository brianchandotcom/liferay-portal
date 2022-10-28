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

import i18n from '../../i18n';
import fetcher from '../fetcher';

const deleteResource = (resource: RequestInfo) => {
	if (confirm(i18n.translate('are-you-sure-you-want-to-delete-this-item'))) {
		return fetcher.delete(resource);
	}
};

export {deleteResource};

export * from './LiferayUserAccounts';
export * from './TestrayBuild';
export * from './TestrayCase';
export * from './TestrayCaseRequirements';
export * from './TestrayCaseResult';
export * from './TestrayCaseTypes';
export * from './TestrayComponent';
export * from './TestrayFactor';
export * from './TestrayFactorCategory';
export * from './TestrayFactorOptions';
export * from './TestrayProductVersion';
export * from './TestrayProject';
export * from './TestrayRequirement';
export * from './TestrayRequirementCase';
export * from './TestrayRoutine';
export * from './TestrayRun';
export * from './TestraySuite';
export * from './TestraySuiteCases';
export * from './TestraySubtask';
export * from './TestraySubtaskCaseResults';
export * from './TestrayTask';
export * from './TestrayTaskUsers';
export * from './TestrayTeam';
export * from './types';
