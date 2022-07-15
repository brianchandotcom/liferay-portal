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

import {gql} from '@apollo/client';

import {UserAccount} from './liferayUserAccount';
import {TestrayBuild} from './testrayBuild';
import {TestrayCase} from './testrayCase';
import {TestrayComponent} from './testrayComponent';
import {TestrayRun} from './testrayRun';

export type TestrayCaseResult = {
	assignedUserId: string;
	attachments: string;
	build?: TestrayBuild;
	case?: TestrayCase;
	closedDate: string;
	commentMBMessageId: string;
	component: TestrayComponent;
	dateCreated: string;
	dateModified: string;
	dueStatus: number;
	errors: string;
	id: number;
	r_buildToCaseResult_c_build?: TestrayBuild;
	r_caseToCaseResult_c_case?: TestrayCase;
	r_componentToCaseResult_c_component?: TestrayComponent;
	r_runToCaseResult_c_run?: TestrayRun;
	r_userToCaseResults_user?: UserAccount;
	run?: TestrayRun;
	startDate: string;
	user?: UserAccount;
	warnings: number;
};

export const getCaseResultsAggregation = gql`
	query getCaseResultAggregation(
		$aggregation: String = ""
		$filter: String = ""
	) {
		caseResultAggregation(aggregation: $aggregation, filter: $filter)
			@rest(
				type: "C_CaseResult"
				path: "caseresults/?aggregationTerms={args.aggregation}&filter={args.filter}&fields=id"
			) {
			facets {
				facetValues {
					numberOfOccurrences
					term
				}
				facetCriteria
			}
		}
	}
`;

export const getCaseResults = gql`
	query getCaseResults(
		$filter: String = ""
		$page: Int = 1
		$pageSize: Int = 20
	) {
		caseResults(filter: $filter, page: $page, pageSize: $pageSize)
			@rest(
				type: "C_CaseResult"
				path: "caseresults?filter={args.filter}&page={args.page}&pageSize={args.pageSize}&nestedFields=case,component.team,build.productVersion,build.routine,run,user&nestedFieldsDepth=3"
			) {
			items {
				assignedUserId
				attachments
				build: r_buildToCaseResult_c_build {
					gitHash
					id
					routine: r_routineToBuilds_c_routine {
						id
						name
					}
					productVersion: r_productVersionToBuilds_c_productVersion {
						name
					}
				}
				case: r_caseToCaseResult_c_case {
					caseType: r_caseTypeToCases_c_caseType {
						name
					}
					component: r_componentToCases_c_component {
						name
					}
					id
					name
					priority
					caseNumber
				}
				closedDate
				commentMBMessageId
				component: r_componentToCaseResult_c_component {
					name
					team: r_teamToComponents_c_team {
						name
					}
				}
				dateCreated
				dateModified
				dueStatus
				errors
				id
				startDate
				run: r_runToCaseResult_c_run {
					externalReferencePK
				}
				user: r_userToCaseResults_user {
					additionalName
					createDate
					emailAddress
					externalReferenceCode
					givenName
					id: uuid
					modifiedDate
					objectDefinitionId
				}
				warnings
			}
			lastPage
			page
			pageSize
			totalCount
		}
	}
`;

export const getCaseResult = gql`
	query getCaseResult($caseResultId: Long!) {
		caseResult(caseResultId: $caseResultId)
			@rest(
				type: "C_CaseResult"
				path: "caseresults/{args.caseResultId}/?nestedFields=case.caseType,component,build.productVersion,build.routine,run,user&nestedFieldsDepth=3"
			) {
			assignedUserId
			attachments
			build: r_buildToCaseResult_c_build {
				gitHash
				routine: r_routineToBuilds_c_routine {
					name
				}
				productVersion: r_productVersionToBuilds_c_productVersion {
					name
				}
			}
			case: r_caseToCaseResult_c_case {
				caseType: r_caseTypeToCases_c_caseType {
					name
				}
				component: r_componentToCases_c_component {
					name
				}
				description
				id
				name
				priority
				steps
			}
			closedDate
			commentMBMessageId
			component: r_componentToCaseResult_c_component
			dateCreated
			dateModified
			dueStatus
			errors
			id
			startDate
			run: r_runToCaseResult_c_run {
				externalReferencePK
			}
			user: r_userToCaseResults_user {
				additionalName
				createDate
				emailAddress
				externalReferenceCode
				givenName
				id: uuid
				modifiedDate
				objectDefinitionId
			}
			warnings
		}
	}
`;
