/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useModal} from '@clayui/modal';
import ClayTable from '@clayui/table';
import classNames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useState} from 'react';

import SegmentsExperimentsContext from '../../../context.es';
import {openPublishModal} from '../../../state/actions.es';
import {DispatchContext} from '../../../state/context.es';
import {SegmentsVariantType} from '../../../types.es';
import {navigateToExperience} from '../../../util/navigation.es';
import {indexToPercentageString} from '../../../util/percentages.es';
import {
	STATUS_DRAFT,
	STATUS_FINISHED_NO_WINNER,
	STATUS_FINISHED_WINNER,
	STATUS_RUNNING,
	STATUS_TERMINATED,
} from '../../../util/statuses.es';
import {ConfirmModal} from '../../ConfirmModal';

function ImprovementCell({control, improvement: initialImprovement}) {
	const number = Number(initialImprovement);
	const improvement = isNaN(number) ? 0 : number;

	if (control) {
		return (
			<ClayTable.Cell className="pr-0 text-danger">
				{sub(Liferay.Language.get('x-loss'), 0).toLowerCase()}
			</ClayTable.Cell>
		);
	}

	return (
		<ClayTable.Cell
			className={classNames(
				'pr-0',
				improvement > 0 ? 'text-success' : 'text-danger'
			)}
		>
			{sub(
				improvement > 0
					? Liferay.Language.get('x-lift')
					: Liferay.Language.get('x-loss'),
				parseFloat(Math.abs(improvement).toFixed(2))
			).toLowerCase()}
		</ClayTable.Cell>
	);
}

function VariantTable({
	experiment,
	onVariantDeletion,
	onVariantEdition,
	selectedSegmentsExperienceId,
	variants,
}) {
	const [openDropdown, setOpenDropdown] = useState(false);
	const [deleteModalState, setDeleteModalState] = useState({
		open: false,
		variantId: null,
	});
	const {editVariantLayoutURL} = useContext(SegmentsExperimentsContext);
	const dispatch = useContext(DispatchContext);

	const {observer, onClose} = useModal({
		onClose: () => {
			setDeleteModalState({open: false, variantId: null});
		},
	});

	const publishable =
		experiment.status.value === STATUS_TERMINATED ||
		experiment.status.value === STATUS_FINISHED_WINNER ||
		experiment.status.value === STATUS_FINISHED_NO_WINNER;

	const showImprovement =
		publishable || experiment.status.value === STATUS_RUNNING;

	return (
		<>
			<ClayTable>
				<ClayTable.Head>
					<ClayTable.Row>
						<ClayTable.Cell expandable headingCell>
							{Liferay.Language.get('name')}
						</ClayTable.Cell>

						{showImprovement && (
							<ClayTable.Cell className="pr-0" headingCell>
								{Liferay.Language.get('improvement')}
							</ClayTable.Cell>
						)}

						{experiment.status.value === STATUS_DRAFT && (
							<ClayTable.Cell>
								<span className="sr-only">
									{Liferay.Language.get('traffic')}
								</span>
							</ClayTable.Cell>
						)}

						{publishable && (
							<ClayTable.Cell>
								<span className="sr-only">
									{Liferay.Language.get('actions')}
								</span>
							</ClayTable.Cell>
						)}
					</ClayTable.Row>
				</ClayTable.Head>

				<ClayTable.Body>
					{variants.map(
						({
							control,
							name,
							segmentsExperienceId,
							segmentsExperimentRelId,
							segmentsExperimentVariantImprovement: improvement,
							split,
							winner,
						}) => (
							<ClayTable.Row
								active={
									segmentsExperienceId ===
									selectedSegmentsExperienceId
								}
								key={segmentsExperimentRelId}
							>
								<ClayTable.Cell expandable headingTitle>
									<ClayButton
										className="lfr-portal-tooltip text-break"
										data-title={name}
										displayType="unstyled"
										onClick={() =>
											navigateToExperience(
												segmentsExperienceId
											)
										}
									>
										{control ? (
											<span className="align-items-center d-flex">
												{winner && (
													<ClayIcon
														className="mr-1 text-success"
														symbol="check-circle-full"
													/>
												)}

												{name}

												<ClayIcon
													className="ml-2"
													symbol="lock"
												/>
											</span>
										) : (
											name
										)}
									</ClayButton>
								</ClayTable.Cell>

								{experiment.status.value === STATUS_DRAFT &&
									control && <ClayTable.Cell />}

								{showImprovement && (
									<ImprovementCell
										control={control}
										improvement={improvement}
									/>
								)}

								{!control && experiment.editable && (
									<ClayTable.Cell>
										<div className="d-flex justify-content-end">
											<ClayButton
												borderless
												className="btn-monospaced"
												displayType="secondary"
												onClick={() =>
													navigateToExperience(
														segmentsExperienceId,
														editVariantLayoutURL
													)
												}
											>
												<ClayIcon symbol="pencil" />
											</ClayButton>

											<ClayDropDown
												active={openDropdown}
												onActiveChange={setOpenDropdown}
												trigger={
													<ClayButton
														aria-label={Liferay.Language.get(
															'show-actions'
														)}
														borderless
														className="btn-monospaced"
														displayType="secondary"
													>
														<ClayIcon symbol="ellipsis-v" />
													</ClayButton>
												}
											>
												<ClayDropDown.ItemList>
													<ClayDropDown.Item
														onClick={() =>
															onVariantEdition({
																name,
																variantId: segmentsExperimentRelId,
															})
														}
													>
														<ClayIcon
															className="c-mr-3 text-4"
															symbol="pencil"
														/>

														{Liferay.Language.get(
															'edit'
														)}
													</ClayDropDown.Item>

													<ClayDropDown.Item
														onClick={() => {
															setDeleteModalState(
																{
																	open: true,
																	variantId: segmentsExperimentRelId,
																}
															);
														}}
													>
														<ClayIcon
															className="c-mr-3 text-4"
															symbol="trash"
														/>

														{Liferay.Language.get(
															'delete'
														)}
													</ClayDropDown.Item>
												</ClayDropDown.ItemList>
											</ClayDropDown>
										</div>
									</ClayTable.Cell>
								)}

								{!publishable && !experiment.editable && (
									<ClayTable.Cell
										align="right"
										aria-label={Liferay.Language.get(
											'traffic-split'
										)}
										className="text-secondary"
									>
										{indexToPercentageString(split)}
									</ClayTable.Cell>
								)}

								{publishable && (
									<ClayTable.Cell>
										<ClayButton
											borderless
											data-title={Liferay.Language.get(
												'publish'
											)}
											displayType="secondary"
											onClick={() => {
												dispatch(
													openPublishModal({
														experienceId: segmentsExperienceId,
														experienceName: name,
													})
												);
											}}
											size="sm"
										>
											<ClayIcon symbol="arrow-right-full" />
										</ClayButton>
									</ClayTable.Cell>
								)}
							</ClayTable.Row>
						)
					)}
				</ClayTable.Body>
			</ClayTable>

			{deleteModalState.open && (
				<ConfirmModal
					modalObserver={observer}
					onCancel={onClose}
					onConfirm={() => {
						onVariantDeletion(deleteModalState.variantId);

						onClose();
					}}
					submitTitle={Liferay.Language.get('delete')}
					title={Liferay.Language.get('delete-variant')}
				>
					<p className="font-weight-bold text-secondary">
						{Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this'
						)}
					</p>
				</ConfirmModal>
			)}
		</>
	);
}

VariantTable.propTypes = {
	onVariantDeletion: PropTypes.func.isRequired,
	onVariantEdition: PropTypes.func.isRequired,
	variants: PropTypes.arrayOf(SegmentsVariantType),
};

export default VariantTable;
