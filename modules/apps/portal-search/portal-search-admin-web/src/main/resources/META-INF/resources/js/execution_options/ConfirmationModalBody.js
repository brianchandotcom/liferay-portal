/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useState} from 'react';

import {EXECUTION_MODES} from '../constants';

function DiskSpaceUsageBar({
	availableDiskSpace,
	isLowOnDiskSpace,
	totalDiskSpace,
	usedDiskSpace,
	usedPercentage,
}) {
	return (
		<>
			<label>{Liferay.Language.get('disk-usage')}</label>

			<div
				className={`progress ${isLowOnDiskSpace && 'progress-warning'}`}
			>
				<div
					aria-valuemax="100"
					aria-valuemin="0"
					aria-valuenow={usedPercentage}
					className="progress-bar"
					role="progressbar"
					style={{width: `${usedPercentage}%`}}
				></div>
			</div>

			<div className="text-3 text-secondary">
				<span>
					{Liferay.Util.sub(
						Liferay.Language.get('used-x-of-x-gb'),
						usedDiskSpace.toFixed(1),
						totalDiskSpace.toFixed(1)
					)}
				</span>

				<span className="float-right">
					{Liferay.Util.sub(
						Liferay.Language.get('x-gb-free'),
						availableDiskSpace.toFixed(1)
					)}
				</span>
			</div>
		</>
	);
}

function DiskSpaceWarning({availableDiskSpace, usedDiskSpace}) {
	return (
		<div className="c-mt-2 text-secondary">
			<span>{Liferay.Language.get('reindex-disk-space-warning')}:</span>

			<ul>
				<li>
					{Liferay.Util.sub(
						Liferay.Language.get('available-disk-space-x'),
						availableDiskSpace.toFixed(1)
					)}
				</li>

				<li>
					{Liferay.Util.sub(
						Liferay.Language.get('current-disk-space-used-x'),
						usedDiskSpace.toFixed(1)
					)}
				</li>
			</ul>

			<span className="c-mt-2">
				{Liferay.Language.get('do-you-still-wish-to-execute-reindex')}
			</span>
		</div>
	);
}

function HideModalCheckBox({portletNamespace}) {
	return (
		<div className="c-pt-4">
			<div className="custom-checkbox custom-control">
				<label>
					<input
						className="custom-control-input"
						id={`${portletNamespace}hideModalCheckbox`}
						type="checkbox"
					/>

					<span className="custom-control-label">
						<span className="custom-control-label-text">
							{Liferay.Language.get('do-not-show-me-this-again')}
						</span>
					</span>
				</label>
			</div>
		</div>
	);
}

function InfoDescription({cmd, executionMode}) {
	return (
		<div className="text-secondary">
			<div className="c-mb-2">
				{Liferay.Language.get('reindex-actions-time-info')}
			</div>

			<div>
				{executionMode === EXECUTION_MODES.CONCURRENT.value ||
				executionMode === EXECUTION_MODES.SYNC.value ||
				cmd === 'reindexDictionaries'
					? Liferay.Language.get(
							'reindex-actions-search-results-available-info'
						)
					: Liferay.Language.get(
							'reindex-actions-search-results-not-available-info'
						)}
			</div>
		</div>
	);
}

export default function ConfirmationModalBody({
	availableDiskSpace,
	cmd,
	executionMode,
	isLowOnDiskSpace,
	portletNamespace,
	usedDiskSpace,
}) {
	const [expand, setExpand] = useState(false);

	const totalDiskSpace = availableDiskSpace + usedDiskSpace;

	const usedPercentage =
		(usedDiskSpace / (availableDiskSpace + usedDiskSpace)) * 100;

	return (
		<div className="reindex-actions-confirmation-modal-body">
			{executionMode === EXECUTION_MODES.CONCURRENT.value ? (
				isLowOnDiskSpace ? (
					<>
						<DiskSpaceUsageBar
							availableDiskSpace={availableDiskSpace}
							isLowOnDiskSpace={isLowOnDiskSpace}
							totalDiskSpace={totalDiskSpace}
							usedDiskSpace={usedDiskSpace}
							usedPercentage={usedPercentage}
						/>

						<DiskSpaceWarning
							availableDiskSpace={availableDiskSpace}
							usedDiskSpace={usedDiskSpace}
						/>
					</>
				) : (
					<>
						<InfoDescription
							cmd={cmd}
							executionMode={executionMode}
						/>

						<div className="c-mb-0 panel" style={{height: '120px'}}>
							<ClayButton
								className="c-pb-0 c-pl-0"
								displayType="link"
								onClick={() => setExpand(!expand)}
							>
								{expand
									? Liferay.Language.get('hide-disk-space')
									: Liferay.Language.get('view-disk-space')}
							</ClayButton>

							{expand && (
								<div className="c-pl-0 c-pr-0 panel-body">
									<DiskSpaceUsageBar
										availableDiskSpace={availableDiskSpace}
										isLowOnDiskSpace={isLowOnDiskSpace}
										totalDiskSpace={totalDiskSpace}
										usedDiskSpace={usedDiskSpace}
										usedPercentage={usedPercentage}
									/>
								</div>
							)}
						</div>

						<HideModalCheckBox
							portletNamespace={portletNamespace}
						/>
					</>
				)
			) : (
				<>
					<InfoDescription cmd={cmd} executionMode={executionMode} />

					<HideModalCheckBox portletNamespace={portletNamespace} />
				</>
			)}
		</div>
	);
}
