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

import PanelComponent from '../../../../../../common/components/panel';
import {InfoPanelType} from '../types';

type HistoryInfoType = {
	arraySortedByDate: InfoPanelType[];
	setShowPanel: (panel: boolean[]) => void;
	showPanel: boolean[];
};

const HistoryInfo = ({
	arraySortedByDate,
	setShowPanel,
	showPanel,
}: HistoryInfoType) => {
	const ContentDescription = ({description}: {description: string}) => (
		<div className="d-flex justify-content-between">
			<div>{description}</div>
		</div>
	);

	const displayHistoryPanel = (index: number) => {
		const supportArray = [...showPanel];

		supportArray[index] = !supportArray[index];

		setShowPanel(supportArray);
	};

	return (
		<div>
			{arraySortedByDate?.map((item: InfoPanelType, index: number) => {
				return (
					<div
						className="bg-neutral-0 dotted-line flex-row history-detail-border p-6 position-relative"
						key={index}
					>
						<div>
							<div className="align-items-center d-flex data-panel-hide float-left justify-content-between w-25">
								{item.date}
							</div>

							<div className="w-100">
								<PanelComponent
									Description={
										<ContentDescription
											description={item.description}
										/>
									}
									hasExpandedButton={true}
									isPanelExpanded={showPanel[index]}
									key={index}
									setIsPanelExpanded={() =>
										displayHistoryPanel(index)
									}
								>
									<div className="justify-content-between layout-show-details ml-auto mt-4 p-3 w-75">
										<div className="d-flex flex-row justify-content-between">
											<div className="align-self-start mt-2">
												<p className="mb-1 text-neutral-7 w-25">
													Amount
												</p>

												<div>{item.detail_Amount}</div>
											</div>

											<div className="align-self-start mt-2 w-50">
												<p className="mb-1 text-neutral-7">
													Injuries Or Fatalities
												</p>

												<div>
													{item.detail_Injuries}
												</div>
											</div>
										</div>

										<div className="align-self-start mt-3">
											<div>{item.detail_details}</div>
										</div>
									</div>
								</PanelComponent>
							</div>
						</div>
					</div>
				);
			})}
		</div>
	);
};

export default HistoryInfo;
