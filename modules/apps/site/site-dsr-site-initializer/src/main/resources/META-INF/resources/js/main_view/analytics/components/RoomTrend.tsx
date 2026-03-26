/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useCallback, useState} from 'react';

import '../../../../css/components/RoomTrend.scss';
import RoomService from '../../../common/services/RoomService';

type TTrendOptions = {
	color?: string;
	icon: string;
	label: string;
	percentage: number;
	useSpritemap?: boolean;
};

function getDegrees(percentage: number) {
	const clampedPercentage = Math.max(0, Math.min(100, percentage));

	return Math.round(-90 + (clampedPercentage / 100) * 180);
}

function getImage(filename: string) {
	return `${Liferay.ThemeDisplay.getPortalURL()}${Liferay.ThemeDisplay.getPathContext()}/o/site-dsr-site-initializer/images/${filename}`;
}

const OPTIONS: Record<string, TTrendOptions> = {
	CLOSED_LOST: {
		color: '#DA1414',
		icon: 'times-circle-full',
		label: Liferay.Language.get('closed-lost'),
		percentage: 15,
	},
	CLOSED_WON: {
		color: '#AA33FF',
		icon: 'champion-cup',
		label: Liferay.Language.get('closed-won'),
		percentage: 70,
		useSpritemap: true,
	},
	COLD: {
		color: '#4B9FFF',
		icon: 'snow',
		label: Liferay.Language.get('cold'),
		percentage: 5,
		useSpritemap: true,
	},
	ENGAGED: {
		color: '#6CE0CC',
		icon: 'comments',
		label: Liferay.Language.get('engaged'),
		percentage: 55,
	},
	HEATING_UP: {
		color: '#FF8133',
		icon: 'heating',
		label: Liferay.Language.get('heating-up'),
		percentage: 45,
		useSpritemap: true,
	},
	HOT: {
		color: '#FF4F45',
		icon: 'hot',
		label: Liferay.Language.get('hot'),
		percentage: 95,
		useSpritemap: true,
	},
	RE_IGNITED: {
		icon: 'reload',
		label: Liferay.Language.get('reignited'),
		percentage: 25,
		useSpritemap: true,
	},
	READY_TO_CLOSE: {
		color: '#5ACA75',
		icon: 'shield-check',
		label: Liferay.Language.get('ready-to-close'),
		percentage: 80,
	},
	WARMING_UP: {
		color: '#FFBB00',
		icon: 'sun',
		label: Liferay.Language.get('warming-up'),
		percentage: 35,
	},
};

const OPTIONS_KEY = [
	OPTIONS.COLD,
	OPTIONS.WARMING_UP,
	OPTIONS.HEATING_UP,
	OPTIONS.ENGAGED,
	OPTIONS.HOT,
	OPTIONS.READY_TO_CLOSE,
	OPTIONS.CLOSED_WON,
	OPTIONS.CLOSED_LOST,
	OPTIONS.RE_IGNITED,
];

const RoomTrend = ({roomId}: {roomId: number}) => {
	const [trendStatus, setTrendStatus] = useState(OPTIONS.COLD);

	const {color, icon, label, percentage, useSpritemap} = trendStatus;

	const updateRoomTrend = useCallback((roomId: number, trend: number) => {
		return RoomService.updateRoom(roomId, {
			trend,
		});
	}, []);

	return (
		<>
			{roomId ? (
				<div className="inline-item inline-item-before room-trend">
					<div>
						<div className="mb-1">
							<p className="font-weight-semi-bold inline-item inline-item-before mr-1">
								{Liferay.Language.get('room-trend')}
							</p>

							<ClayIcon
								className="text-secondary"
								symbol="question-circle-full"
							/>
						</div>

						<DropDown
							closeOnClick
							trigger={
								<ClayButton
									className="align-items-center d-flex font-weight-normal justify-content-between px-2 room-trend-button"
									displayType="secondary"
								>
									<span className="align-items-center d-flex flex-grow-1 overflow-hidden">
										<ClayIcon
											className="flex-shrink-0 mr-2"
											color={color}
											fontSize={16}
											spritemap={
												useSpritemap
													? getImage(
															'room_trend_icons.svg'
														)
													: undefined
											}
											symbol={icon}
										/>

										<span className="room-trend-button-text text-left text-truncate">
											{label}
										</span>
									</span>

									<ClayIcon
										className="flex-shrink-0 ml-2"
										symbol="caret-double"
									/>
								</ClayButton>
							}
						>
							<DropDown.ItemList>
								{OPTIONS_KEY.map((option, index) => (
									<DropDown.Item
										key={index}
										onClick={() => {
											updateRoomTrend(
												roomId,
												option.percentage
											).then(({trend}) => {
												const updateTrend =
													OPTIONS_KEY.find(
														(option) =>
															option.percentage ===
															trend
													);

												if (updateTrend) {
													setTrendStatus(updateTrend);
												}
											});
										}}
									>
										<span className="mr-4">
											<ClayIcon
												color={option.color}
												fontSize={16}
												spritemap={
													option.useSpritemap
														? getImage(
																'room_trend_icons.svg'
															)
														: undefined
												}
												symbol={option.icon}
											/>
										</span>

										{option.label}
									</DropDown.Item>
								))}
							</DropDown.ItemList>
						</DropDown>
					</div>

					<div className="gauge-container inline-item-after ml-4">
						<img
							alt={Liferay.Language.get('room-trend-semicircle')}
							className="room-trend-semicircle"
							src={getImage('room_trend_semicircle.svg')}
						></img>

						<img
							alt={Liferay.Language.get('room-trend-pointer')}
							className="room-trend-pointer"
							src={getImage('room_trend_pointer.svg')}
							style={{
								transform: `rotate(${getDegrees(percentage)}deg) translateZ(0)`,
							}}
						></img>
					</div>
				</div>
			) : null}
		</>
	);
};

export default RoomTrend;
