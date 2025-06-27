/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayBreadcrumb from '@clayui/breadcrumb';
import React, {useMemo} from 'react';

import {useCache} from '../contexts/CacheContext';
import {useSelector, useStateDispatch} from '../contexts/StateContext';
import selectStructureChildren from '../selectors/selectStructureChildren';
import selectStructureLocalizedLabel from '../selectors/selectStructureLocalizedLabel';
import selectStructureUuid from '../selectors/selectStructureUuid';
import {RepeatableGroup, Structure, Structures} from '../types/Structure';
import {Uuid} from '../types/Uuid';
import getReferencedStructureLabel from '../utils/getReferencedStructureLabel';

type Path = {label: string; uuid: Uuid}[];

export default function Breadcrumb({uuid}: {uuid: Uuid}) {
	const dispatch = useStateDispatch();

	const children = useSelector(selectStructureChildren);

	const structureLabel = useSelector(selectStructureLocalizedLabel);
	const structureUuid = useSelector(selectStructureUuid);

	const {data: structures} = useCache('structures');

	const items = useMemo(() => {
		const path = getPath(uuid, children, structures, [
			{label: structureLabel, uuid: structureUuid},
		]);

		if (!path) {
			return [];
		}

		return path.map((item) => {
			if (item.uuid === uuid) {
				return {
					active: true,
					label: item.label,
				};
			}

			return {
				label: item.label,
				onClick: () => {
					dispatch({
						selection: [item.uuid],
						type: 'set-selection',
					});
				},
			};
		});
	}, [children, dispatch, structureLabel, structureUuid, structures, uuid]);

	return (
		<div className="mb-3">
			<ClayBreadcrumb items={items} />
		</div>
	);
}

function getPath(
	uuid: Uuid,
	children: (Structure | RepeatableGroup)['children'],
	structures: Structures,
	path: Path = []
): Path | null {
	for (const child of children.values()) {
		if (child.uuid === uuid) {
			if (child.type === 'referenced-structure') {
				path.push({
					label: getReferencedStructureLabel(child.erc, structures),
					uuid: child.uuid,
				});
			}
			else {
				path.push({
					label: child!.label[
						Liferay.ThemeDisplay.getDefaultLanguageId()
					]!,
					uuid: child.uuid,
				});
			}

			return path;
		}

		if (child.type === 'referenced-structure') {
			const structure = structures.get(child.erc);

			path.push({
				label: getReferencedStructureLabel(child.erc, structures),
				uuid: child.uuid,
			});

			if (structure) {
				const nextPath = getPath(
					uuid,
					structure.children,
					structures,
					path
				);

				if (nextPath) {
					return nextPath;
				}
			}
		}
		else if (child.type === 'repeatable-group') {
			path.push({
				label: child!.label[
					Liferay.ThemeDisplay.getDefaultLanguageId()
				]!,
				uuid: child.uuid,
			});

			const nextPath = getPath(uuid, child.children, structures, path);

			if (nextPath) {
				return nextPath;
			}
		}
	}

	return null;
}
