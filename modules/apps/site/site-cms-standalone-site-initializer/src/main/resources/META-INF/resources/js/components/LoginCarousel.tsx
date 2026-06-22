/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import '../../css/components/LoginCarousel.scss';

interface Slide {
	description: string;
	image: string;
	title: string;
}

const imagesPath = `${Liferay.ThemeDisplay.getPathContext()}/o/site-cms-standalone-site-initializer/images`;

const SLIDES: Slide[] = [
	{
		description: Liferay.Language.get(
			'build-once-publish-everywhere-with-structured-reusable-content-that-scales-across-every-channel'
		),
		image: `${imagesPath}/carousel-1.png`,
		title: Liferay.Language.get('structured-content-for-every-channel'),
	},
	{
		description: Liferay.Language.get(
			'centralize-all-your-images-videos-and-documents-in-one-unified-media-library'
		),
		image: `${imagesPath}/carousel-2.png`,
		title: Liferay.Language.get('all-your-assets-one-smart-library'),
	},
	{
		description: Liferay.Language.get(
			'create-isolated-workspaces-for-teams-brands-or-projects-all-in-one-platform'
		),
		image: `${imagesPath}/carousel-3.png`,
		title: Liferay.Language.get('collaboration-without-friction'),
	},
	{
		description: Liferay.Language.get(
			'built-in-governance-tools-reduce-risks-and-keep-every-asset-on-brand-and-compliant'
		),
		image: `${imagesPath}/carousel-4.png`,
		title: Liferay.Language.get('control-every-piece-of-content'),
	},
];

const EXIT_MS = 350;
const INTERVAL_MS = 7000;

export default function LoginCarousel() {
	const [current, setCurrent] = useState<number>(0);
	const [exiting, setExiting] = useState<number | null>(null);
	const [paused, setPaused] = useState<boolean>(false);

	function goTo(index: number) {
		if (index === current) {
			return;
		}

		setExiting(current);
		setCurrent((index + SLIDES.length) % SLIDES.length);
	}

	useEffect(() => {
		if (exiting === null) {
			return;
		}

		const timeoutId = setTimeout(() => setExiting(null), EXIT_MS);

		return () => clearTimeout(timeoutId);
	}, [exiting]);

	useEffect(() => {
		if (paused) {
			return;
		}

		const intervalId = setInterval(() => {
			setExiting(current);
			setCurrent((current + 1) % SLIDES.length);
		}, INTERVAL_MS);

		return () => clearInterval(intervalId);
	}, [current, paused]);

	return (
		<div className="border login-carousel overflow-hidden position-relative">
			{SLIDES.map((slide, index) => {
				const className = classNames(
					'align-items-center d-flex flex-column login-carousel-slide position-absolute',
					{
						'is-active': index === current,
						'is-out': index === exiting,
					}
				);

				return (
					<div className={className} key={index}>
						<div className="login-carousel-card position-absolute">
							<img
								alt=""
								className="login-carousel-placeholder shadow-lg"
								src={slide.image}
							/>
						</div>

						<div className="c-gap-2 d-flex flex-column login-carousel-text position-absolute text-center">
							<h2 className="font-weight-bold login-carousel-title m-0">
								{slide.title}
							</h2>

							<p className="login-carousel-description m-0 text-secondary">
								{slide.description}
							</p>
						</div>
					</div>
				);
			})}

			<div className="align-items-center c-gap-2 d-flex login-carousel-nav position-absolute">
				{SLIDES.map((_, index) => (
					<button
						aria-label={`Slide ${index + 1}`}
						className={classNames(
							'btn-unstyled login-carousel-dot',
							{
								'is-active': index === current,
							}
						)}
						key={index}
						onClick={() => goTo(index)}
						type="button"
					/>
				))}

				<ClayButton
					aria-label={
						paused
							? Liferay.Language.get('Resume')
							: Liferay.Language.get('Pause')
					}
					className={classNames(
						'align-items-center d-flex justify-content-center login-carousel-pause position-absolute rounded-pill',
						{
							'is-paused': paused,
						}
					)}
					displayType="unstyled"
					onClick={() => setPaused((value) => !value)}
				>
					<ClayIcon
						fontSize={12}
						symbol={paused ? 'play' : 'pause'}
					/>
				</ClayButton>
			</div>
		</div>
	);
}
