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

import {useContext} from 'react';

import {ApplicationPropertiesContext} from '../../context/ApplicationPropertiesContext';
import {TestrayCaseResultIssue, testrayIssueImpl} from '../../services/rest';

type JiraLinkProps = {
	issue: TestrayCaseResultIssue;
};

const splitTaskName = (name: string) => name.split(testrayIssueImpl.DELIMITER);

const JiraLink: React.FC<JiraLinkProps> = ({issue}) => {
	const {jiraBaseURL} = useContext(ApplicationPropertiesContext);

	const [name] = splitTaskName(issue.name);

	return (
		<a
			className="mr-2"
			href={`${jiraBaseURL}/browse/${name}`}
			target="_blank"
		>
			{name}
		</a>
	);
};

export {splitTaskName};

export default JiraLink;
