/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useEffect, useState} from 'react';

import './ModalBindToRootObject.scss';

import ClayButton from '@clayui/button';
import {
	Input,
	SingleSelect,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';

interface ModalBindToRootObjectProps {
	baseResourceURL: string;
	onVisibilityChange: () => void;
	selectedObjectToBind?: ObjectDefinition;
}

interface RootObjectPathOption {
	ancestors?: {
		label: string;
		objectRelationshipId: number;
	}[];
	isAncestor?: boolean;
	isRootObject?: boolean;
	label: string;
	objectRelationshipId: number;
}

export function ModalBindToRootObject({
	baseResourceURL,
	onVisibilityChange,
	selectedObjectToBind,
}: ModalBindToRootObjectProps) {
	const [loading, setLoading] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => onVisibilityChange(),
	});
	const [currentDepth, setCurrentDepth] = useState(0);
	const [currentRelationshipId, setCurrentRelationshipId] = useState(0);

	const [allRootObjectOptions, setAllRootObjectOptions] = useState<
		RootObjectPathOption[][]
	>([]);

	const [selectedRootObjects, setSelectedRootObjects] = useState<
		RootObjectPathOption[]
	>([]);

	const onSubmit = async () => {
		const objectRelationshipIds = selectedRootObjects.map(
			(selectedObject) => selectedObject.objectRelationshipId
		);

		const response = await fetch(
			createResourceURL(baseResourceURL, {
				objectRelationshipIds:
					objectRelationshipIds.length > 1
						? objectRelationshipIds.join(', ')
						: objectRelationshipIds[0],
				p_p_resource_id: '/object_definitions/bind_object_definitions',
			}).toString()
		);

		if (response.ok) {
			openToast({
				message: Liferay.Language.get(
					'object-successfully-bound-to-root'
				),
				type: 'success',
			});

			onClose();

			setTimeout(() => window.location.reload(), 500);
		}
	};

	const filterRootObjectsByDepth = (
		depth: number,
		newAllRootObjectOptions: RootObjectPathOption[][],
		newSelectedRootObjects: RootObjectPathOption[]
	) => {
		const filteredSelectedRootObjects = newSelectedRootObjects.filter(
			(_, index) => index <= depth
		);

		setAllRootObjectOptions(
			newAllRootObjectOptions.filter((_, index) => index <= depth)
		);

		setSelectedRootObjects(
			filteredSelectedRootObjects.map((selectedObject, index) => {
				return {
					...selectedObject,
					isRootObject: newSelectedRootObjects.length === index + 1,
				};
			})
		);
	};

	const handleSelectRootObject = (
		option: RootObjectPathOption,
		depth: number
	) => {
		setLoading(true);

		const newSelectedRootObjects = selectedRootObjects;

		newSelectedRootObjects[depth] = option;

		if (option.ancestors) {
			let ancestorsDepth = depth;

			const newAllRootObjectOptions = allRootObjectOptions;

			option.ancestors.forEach((ancestor) => {
				newSelectedRootObjects[ancestorsDepth + 1] = {
					...ancestor,
					isAncestor: true,
				};

				newAllRootObjectOptions[ancestorsDepth + 1] = [ancestor];

				ancestorsDepth++;
			});

			filterRootObjectsByDepth(
				ancestorsDepth,
				newAllRootObjectOptions,
				newSelectedRootObjects
			);

			setTimeout(() => setLoading(false), 500);

			return;
		}

		if (depth < 3) {
			setCurrentDepth(depth + 1);
			setCurrentRelationshipId(option.objectRelationshipId);
		}

		filterRootObjectsByDepth(
			depth,
			allRootObjectOptions,
			newSelectedRootObjects
		);

		setTimeout(() => setLoading(false), 500);

		return;
	};

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			if (currentDepth <= 4) {
				const response = await fetch(
					createResourceURL(baseResourceURL, {
						depth: currentDepth,
						objectDefinitionId:
							currentRelationshipId === 0
								? selectedObjectToBind?.id
								: 0,
						objectRelationshipId:
							currentRelationshipId !== 0
								? currentRelationshipId
								: 0,
						p_p_resource_id:
							'/object_definitions/get_object_relationship_edge_candidates',
					}).toString()
				);

				const responseJSON = (await response.json()) as RootObjectPathOption[];

				if (!!responseJSON.length && allRootObjectOptions.length <= 4) {
					const newRootObjectOptions = allRootObjectOptions;

					newRootObjectOptions[currentDepth] = responseJSON;

					setAllRootObjectOptions(newRootObjectOptions);
				}
			}

			setLoading(false);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [baseResourceURL, currentDepth, currentRelationshipId]);

	return (
		<ClayModal center observer={observer} size="lg">
			<ClayModal.Header>
				{Liferay.Language.get('bind-to-root-object')}
			</ClayModal.Header>

			<ClayModal.Body className="lfr__object-modal-bind-to-root-object-body">
				<span className="lfr__object-modal-bind-to-root-object-description">
					{Liferay.Language.get(
						'you-can-bind-an-object-to-a-root-through-a-valid-one-to-many-relationship'
					)}
				</span>

				<Input
					disabled
					label={Liferay.Language.get('object-to-be-bound')}
					value={getLocalizableLabel(
						selectedObjectToBind?.defaultLanguageId as Liferay.Language.Locale,
						selectedObjectToBind?.label,
						selectedObjectToBind?.name
					)}
				/>

				{loading ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
					allRootObjectOptions.map((rootObjectOptions, index) => (
						<SingleSelect<RootObjectPathOption>
							contentRight={
								selectedRootObjects[index]?.isRootObject && (
									<ClayLabel displayType="info">
										{Liferay.Language.get('root-object')}
									</ClayLabel>
								)
							}
							key={
								selectedRootObjects[index]
									?.objectRelationshipId ?? index
							}
							label={
								index === 0
									? Liferay.Language.get(
											'select-the-root-object-path'
									  )
									: ''
							}
							onChange={async (option) => {
								handleSelectRootObject(option, index);
							}}
							options={rootObjectOptions}
							readonly={
								selectedRootObjects[index]?.isAncestor ?? false
							}
							value={selectedRootObjects[index]?.label ?? ''}
						/>
					))
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group key={1} spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedRootObjects[0]}
							displayType="primary"
							onClick={() => onSubmit()}
							type="submit"
						>
							{Liferay.Language.get('bind-to-root')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
