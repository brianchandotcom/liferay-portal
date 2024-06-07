/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEventListener} from '@liferay/frontend-js-react-web';
import {sub} from 'frontend-js-web';
import {useEffect} from 'react';

import {Criteria} from '../../../types/Criteria';
import {
	POSITIONS,
	Source,
	Target,
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSendMovementMessage,
	useSetMovementTarget,
} from '../../contexts/KeyboardMovementContext';
import {KeyboardKey} from '../../types/KeyboardKey';
import dropInGroup from '../../utils/dropInGroup';
import dropInRow from '../../utils/dropInRow';
import getGroup from '../../utils/getGroup';

export const ACTION_TYPES = {
	add: 'add',
	move: 'move',
} as const;

const DIRECTIONS = {
	down: 'down',
	up: 'up',
} as const;

type Direction = (typeof DIRECTIONS)[keyof typeof DIRECTIONS];

type Contributor = {
	criteriaMap: Criteria;
	propertyKey: string;
};

type Props = {
	contributors: Contributor[];
	onMove: (criteria: Criteria, propertyKey: PropertyKey) => void;
};

export default function KeyboardMovementManager({contributors, onMove}: Props) {
	const source = useMovementSource()!;
	const target = useMovementTarget();
	const sendMessage = useSendMovementMessage();
	const setTarget = useSetMovementTarget();
	const disableMovement = useDisableKeyboardMovement();

	const criteria = getCriteria(contributors, source.propertyKey);

	useEventListener(
		'keydown',
		(event) => {
			event.stopPropagation();
			event.preventDefault();

			const key = (<KeyboardEvent>event).key as KeyboardKey;

			// Move down

			if (key === 'ArrowDown') {
				const nextTarget = getNextTarget(
					source,
					target!,
					DIRECTIONS.down,
					criteria!
				);

				if (nextTarget) {
					setTarget(nextTarget);

					sendMessage(
						sub(
							Liferay.Language.get(
								'targeting-x-of-item-x-of-group-x'
							),
							nextTarget.position,
							nextTarget.index.toString(),
							getGroupNumber(nextTarget.groupId)!
						)
					);
				}
			}

			// Move up

			else if (key === 'ArrowUp') {
				const nextTarget = getNextTarget(
					source,
					target!,
					DIRECTIONS.up,
					criteria!
				);

				if (nextTarget) {
					setTarget(nextTarget);

					sendMessage(
						sub(
							Liferay.Language.get(
								'targeting-x-of-item-x-of-group-x'
							),
							nextTarget.position,
							nextTarget.index.toString(),
							getGroupNumber(nextTarget.groupId)!
						)
					);
				}
			}

			// Move to end

			else if (key === 'End') {
				const nextTarget = {
					groupId: criteria!.groupId,
					index: criteria!.items.length - 1,
					position: POSITIONS.bottom,
				};

				if (nextTarget) {
					setTarget(nextTarget);

					sendMessage(
						sub(
							Liferay.Language.get(
								'targeting-x-of-item-x-of-group-x'
							),
							nextTarget.position,
							nextTarget.index.toString(),
							getGroupNumber(nextTarget.groupId)!
						)
					);
				}
			}

			// Execute action

			else if (key === 'Enter') {
				let nextCriteria;

				if (target!.position === POSITIONS.middle) {
					nextCriteria = dropInRow(criteria!, source, target!);
				}
				else {
					nextCriteria = dropInGroup(criteria!, source, target!);
				}

				onMove(nextCriteria, source.propertyKey);

				sendMessage(
					sub(
						Liferay.Language.get(
							'x-placed-on-x-of-item-x-of-group-x'
						),
						source.label,
						target!.position,
						target!.index.toString(),
						getGroupNumber(target!.groupId)!
					)
				);

				disableMovement();
			}

			// Disable movement

			else if (key === 'Escape') {
				disableMovement();
			}

			// Move to start

			else if (key === 'Home') {
				const nextTarget = {
					groupId: criteria!.groupId,
					index: 0,
					position: POSITIONS.top,
				};

				setTarget(nextTarget);

				sendMessage(
					sub(
						Liferay.Language.get(
							'targeting-x-of-item-x-of-group-x'
						),
						nextTarget.position,
						nextTarget.index.toString(),
						getGroupNumber(nextTarget.groupId)!
					)
				);
			}
		},
		true,
		document
	);

	useEffect(() => {
		if (target) {
			return;
		}

		const initialTarget = getInitialTarget(source, criteria)!;

		if (isValidTarget(initialTarget, source)) {
			setTarget(initialTarget);

			sendMessage(
				sub(
					Liferay.Language.get(
						'use-up-and-down-arrows-to-move-the-item-and-press-enter-to-place-it-in-desired-position.-currently-targeting-x-of-item-x-of-group-x'
					),
					initialTarget.position,
					initialTarget.index.toString(),
					getGroupNumber(initialTarget.groupId)!
				)
			);
		}
		else {
			disableMovement();
		}
	}, [criteria, disableMovement, sendMessage, setTarget, source, target]);

	return null;
}

function getCriteria(
	contributors: Contributor[],
	propertyKey: Source['propertyKey']
) {
	return (
		contributors.find(
			(contributor) => contributor.propertyKey === propertyKey
		)?.criteriaMap ?? null
	);
}

function getParentGroup(
	groupId: Criteria['groupId'],
	criteria: Criteria
): Criteria | undefined {
	for (const child of criteria.items) {
		if ('groupId' in child) {
			if (child.groupId === groupId) {
				return criteria;
			}

			const parentGroup = getParentGroup(groupId, child);

			if (parentGroup) {
				return parentGroup;
			}
		}
	}
}

function getGroupNumber(groupId: Criteria['groupId']) {
	return groupId.split('_').pop();
}

function isValidTarget(target: Target, source: Source) {
	return !(
		!target ||
		(target.groupId === source.groupId && target.index === source.index)
	);
}

function getInitialTarget(source: Source, criteria: Criteria | null) {
	const actionType = source.groupId ? ACTION_TYPES.move : ACTION_TYPES.add;

	if (actionType === ACTION_TYPES.add) {
		if (criteria) {
			return {
				groupId: criteria.groupId,
				index: criteria.items.length - 1,
				position: POSITIONS.bottom,
			};
		}
		else {
			return {
				groupId: 'root',
				index: 0,
				position: POSITIONS.middle,
			};
		}
	}
	else if (actionType === ACTION_TYPES.move) {
		return (
			getNextTarget(
				source,
				{
					groupId: source.groupId!,
					index: source.index!,
					position: POSITIONS.middle,
				},
				DIRECTIONS.down,
				criteria!
			) ||
			getNextTarget(
				source,
				{
					groupId: source.groupId!,
					index: source.index!,
					position: POSITIONS.middle,
				},
				DIRECTIONS.up,
				criteria!
			)
		);
	}
}

function getNextTarget(
	source: Source,
	target: Target,
	direction: Direction,
	criteria: Criteria
): Target | null {
	const {groupId, index, position} = target;

	const group = getGroup(groupId, criteria)!;

	const item = group.items[index];

	let nextTarget = target;

	if (direction === DIRECTIONS.up) {
		if (position === POSITIONS.bottom) {
			if ('groupId' in item) {
				nextTarget = {
					groupId: item.groupId,
					index: item.items.length - 1,
					position: POSITIONS.bottom,
				};
			}
			else {
				nextTarget = {
					groupId,
					index,
					position: POSITIONS.middle,
				};
			}
		}
		else if (position === POSITIONS.middle) {
			nextTarget = {
				groupId,
				index,
				position: POSITIONS.top,
			};
		}
		else if (position === POSITIONS.top) {
			if (index === 0) {
				const parentGroup = getParentGroup(groupId, criteria);

				if (!parentGroup) {
					return null;
				}
				else if (parentGroup.items.indexOf(group) === 0) {
					nextTarget = {
						groupId: parentGroup.groupId,
						index: 0,
						position: POSITIONS.top,
					};
				}
				else {
					nextTarget = {
						groupId: parentGroup.groupId,
						index: parentGroup.items.indexOf(group),
						position: POSITIONS.top,
					};
				}
			}
			else {
				nextTarget = {
					groupId,
					index: index - 1,
					position: POSITIONS.bottom,
				};
			}
		}
	}
	else if (direction === DIRECTIONS.down) {
		if (position === POSITIONS.top) {
			if ('groupId' in item) {
				nextTarget = {
					groupId: item.groupId,
					index: 0,
					position: POSITIONS.top,
				};
			}
			else {
				nextTarget = {
					groupId,
					index,
					position: POSITIONS.middle,
				};
			}
		}
		else if (position === POSITIONS.middle) {
			nextTarget = {
				groupId,
				index,
				position: POSITIONS.bottom,
			};
		}
		else if (position === POSITIONS.bottom) {
			if (index === group.items.length - 1) {
				const parentGroup = getParentGroup(groupId, criteria);

				if (!parentGroup) {
					return null;
				}
				else if (
					parentGroup.items.indexOf(group) ===
					parentGroup.items.length - 1
				) {
					nextTarget = {
						groupId: parentGroup.groupId,
						index: parentGroup.items.length - 1,
						position: POSITIONS.bottom,
					};
				}
				else {
					nextTarget = {
						groupId: parentGroup.groupId,
						index: parentGroup.items.indexOf(group),
						position: POSITIONS.bottom,
					};
				}
			}
			else {
				nextTarget = {
					groupId,
					index: index + 1,
					position: POSITIONS.top,
				};
			}
		}
	}

	if (isValidTarget(nextTarget!, source)) {
		return nextTarget;
	}

	return getNextTarget(source, nextTarget, direction, criteria);
}
