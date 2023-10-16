/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayModal, {useModal} from '@clayui/modal';
import {
	Input,
	SingleSelect,
	getLocalizableLabel,
	openToast,
} from '@liferay/object-js-components-web';
import {createResourceURL, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import './ModalBindToRootObject.scss';

interface ModalBindToRootObjectDefinitionProps {
	baseResourceURL: string;
	onVisibilityChange: () => void;
	selectedObjectDefinitionToBind?: ObjectDefinition;
}

interface TreeEdgeOption {
	ancestors?: {
		label: string;
		objectRelationshipId: number;
		value: number;
	}[];
	isAncestor?: boolean;
	isRoot?: boolean;
	label: string;
	objectRelationshipId: number;
	value?: number;
}

const SingleSelectTrigger = React.forwardRef<HTMLDivElement>(
	({children, ...otherProps}, ref) => (
		<div
			ref={ref}
			style={{
				alignItems: 'center',
				display: 'flex',
				justifyContent: 'space-between',
			}}
			tabIndex={0}
			{...otherProps}
		>
			{children}

			<ClayLabel displayType="info">
				{Liferay.Language.get('root-object')}
			</ClayLabel>
		</div>
	)
);

export function ModalBindToRootObjectDefinition({
	baseResourceURL,
	onVisibilityChange,
	selectedObjectDefinitionToBind,
}: ModalBindToRootObjectDefinitionProps) {
	const [loading, setLoading] = useState(false);
	const {observer, onClose} = useModal({
		onClose: () => onVisibilityChange(),
	});
	const [currentDepth, setCurrentDepth] = useState(0);
	const [
		currentObjectRelationshipId,
		setCurrentObjectRelationshipId,
	] = useState(0);

	const [allTreeEdgeOptions, setAllTreeEdgeOptions] = useState<
		TreeEdgeOption[][]
	>([]);

	const [selectedTreeEdgeOptions, setSelectedTreeEdgeOptions] = useState<
		TreeEdgeOption[]
	>([]);

	const onSubmit = async () => {
		const objectRelationshipIds = selectedTreeEdgeOptions.map(
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

	const filterTreeEdgeOptionsByDepth = (
		depth: number,
		newTreeEdgeOptions: TreeEdgeOption[][],
		newSelectedTreeEdgeOptions: TreeEdgeOption[]
	) => {
		const filteredSelectedTreeEdgeOptions = newSelectedTreeEdgeOptions.filter(
			(_, index) => index <= depth
		);

		setAllTreeEdgeOptions(
			newTreeEdgeOptions.filter((_, index) => index <= depth)
		);

		setSelectedTreeEdgeOptions(
			filteredSelectedTreeEdgeOptions.map(
				(selectedTreeEdgeOption, index) => {
					return {
						...selectedTreeEdgeOption,
						isRoot: newSelectedTreeEdgeOptions.length === index + 1,
					};
				}
			)
		);
	};

	const handleSelectTreeEdgeOption = (
		selectedTreeEdgeOption: TreeEdgeOption,
		depth: number
	) => {
		setLoading(true);

		const newSelectedTreeEdgeOptions = selectedTreeEdgeOptions;

		newSelectedTreeEdgeOptions[depth] = selectedTreeEdgeOption;

		if (selectedTreeEdgeOption.ancestors) {
			let ancestorsDepth = depth;

			const newAllTreeEdgeOptions = allTreeEdgeOptions;

			selectedTreeEdgeOption.ancestors.forEach((ancestor) => {
				newSelectedTreeEdgeOptions[ancestorsDepth + 1] = {
					...ancestor,
					isAncestor: true,
				};

				newAllTreeEdgeOptions[ancestorsDepth + 1] = [ancestor];

				ancestorsDepth++;
			});

			filterTreeEdgeOptionsByDepth(
				ancestorsDepth,
				newAllTreeEdgeOptions,
				newSelectedTreeEdgeOptions
			);

			setTimeout(() => setLoading(false), 500);

			return;
		}

		if (depth < 3) {
			setCurrentDepth(depth + 1);
			setCurrentObjectRelationshipId(
				selectedTreeEdgeOption.objectRelationshipId
			);
		}

		filterTreeEdgeOptionsByDepth(
			depth,
			allTreeEdgeOptions,
			newSelectedTreeEdgeOptions
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
							currentObjectRelationshipId === 0
								? selectedObjectDefinitionToBind?.id
								: 0,
						objectRelationshipId:
							currentObjectRelationshipId !== 0
								? currentObjectRelationshipId
								: 0,
						p_p_resource_id:
							'/object_definitions/get_object_relationship_edge_candidates',
					}).toString()
				);

				const treeEdgeOptionsResponseJSON = (await response.json()) as TreeEdgeOption[];

				if (
					!!treeEdgeOptionsResponseJSON.length &&
					allTreeEdgeOptions.length <= 4
				) {
					const newTreeEdgeOptionsResponseJSON = treeEdgeOptionsResponseJSON.map(
						(treeEdgeOption) => {
							if (treeEdgeOption.ancestors) {
								const newAncestors = treeEdgeOption.ancestors.map(
									(ancestor) => ({
										...ancestor,
										value: ancestor.objectRelationshipId,
									})
								);

								return {
									...treeEdgeOption,
									ancestors: newAncestors,
									value: treeEdgeOption.objectRelationshipId,
								};
							}

							return {
								...treeEdgeOption,
								value: treeEdgeOption.objectRelationshipId,
							};
						}
					) as TreeEdgeOption[];

					const newTreeEdgeOptions = allTreeEdgeOptions;

					newTreeEdgeOptions[
						currentDepth
					] = newTreeEdgeOptionsResponseJSON;

					setAllTreeEdgeOptions(newTreeEdgeOptions);
				}
			}

			setLoading(false);
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [baseResourceURL, currentDepth, currentObjectRelationshipId]);

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
						selectedObjectDefinitionToBind?.defaultLanguageId as Liferay.Language.Locale,
						selectedObjectDefinitionToBind?.label,
						selectedObjectDefinitionToBind?.name
					)}
				/>

				{loading ? (
					<ClayLoadingIndicator displayType="secondary" size="sm" />
				) : (
					allTreeEdgeOptions.map((treeEdgeOptions, index) => (
						<SingleSelect<TreeEdgeOption>
							as={
								selectedTreeEdgeOptions[index]?.isRoot
									? SingleSelectTrigger
									: 'button'
							}
							disabled={
								selectedTreeEdgeOptions[index]?.isAncestor ??
								false
							}
							items={treeEdgeOptions}
							key={
								selectedTreeEdgeOptions[index]
									?.objectRelationshipId ?? index
							}
							label={
								index === 0
									? Liferay.Language.get(
											'select-the-root-object-path'
									  )
									: ''
							}
							onSelectionChange={(value) => {
								const isOptionAlreadySelected = selectedTreeEdgeOptions.some(
									({objectRelationshipId}) =>
										objectRelationshipId === value
								);

								const selectedTreeEdgeOption = treeEdgeOptions.find(
									(treeEdgeOption) =>
										treeEdgeOption.value === value
								);

								if (
									!isOptionAlreadySelected &&
									selectedTreeEdgeOption
								) {
									handleSelectTreeEdgeOption(
										selectedTreeEdgeOption,
										index
									);
								}
							}}
							selectedKey={selectedTreeEdgeOptions[index]?.value}
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
							disabled={!selectedTreeEdgeOptions[0]}
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
