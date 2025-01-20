/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {
	MarketplaceView,
	ProductPurchase,
	useMarketplaceContext,
} from '@liferay/marketplace-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

export enum States {
	ERROR,
	CONFIRM_INSTALLATION,
	IN_PROGRESS,
	NO_PROJECT,
	NO_RESOURCES,
	SUCCESS,
}

type MarketplacePurchaseProps = {
	onClickInstall: () => void;
	projectId?: string;
	state: States;
};

const MARKETPLACE_ADMIN_EMAIL = 'marketplace-admin@liferay.com';

export function MarketplacePurchase({
	onClickInstall,
	projectId,
	state,
}: MarketplacePurchaseProps) {
	const {setView} = useMarketplaceContext();

	const secondaryButtonProps = {
		children: Liferay.Language.get('cancel'),
		onClick: () => setView(MarketplaceView.STOREFRONT),
	} as React.HTMLAttributes<HTMLButtonElement>;

	if (state === States.SUCCESS) {
		return (
			<ProductPurchase.Body
				secondaryButtonProps={secondaryButtonProps}
				title={Liferay.Language.get('success')}
			>
				<span className="mx-1">
					{Liferay.Language.get(
						'your-application-has-been-installed-wait-a-few-moments-for-it-to-become-available'
					)}
				</span>
			</ProductPurchase.Body>
		);
	}

	if (state === States.ERROR) {
		return (
			<ProductPurchase.Body
				secondaryButtonProps={secondaryButtonProps}
				title={Liferay.Language.get('error')}
			>
				<span className="mx-1">
					{Liferay.Language.get(
						'sorry-was-not-possible-to-install-your-app-you-can-try-again-if-the-problem-persist-contact-via'
					)}
				</span>

				<a href={`mailto:${MARKETPLACE_ADMIN_EMAIL}`}>
					{MARKETPLACE_ADMIN_EMAIL}
				</a>
			</ProductPurchase.Body>
		);
	}

	if (state === States.NO_PROJECT) {
		return (
			<ProductPurchase.Body
				secondaryButtonProps={secondaryButtonProps}
				title={Liferay.Language.get('no-cloud-project-available')}
			>
				<p className="text-red">
					{Liferay.Language.get(
						'you-currently-do-not-have-access-to-any-cloud-projects-please-login-as-a-user-that-has-access-to-a-project-or-contact-your-project-administrator-to-add-you-to-a-project'
					)}
				</p>
			</ProductPurchase.Body>
		);
	}

	if (state === States.NO_RESOURCES) {
		return (
			<ProductPurchase.Body
				primaryButtonProps={{
					borderless: true,
					children: sub(
						Liferay.Language.get('contact-x'),
						Liferay.Language.get('support')
					),
				}}
				secondaryButtonProps={secondaryButtonProps}
				title={Liferay.Language.get('insufficient-resources')}
			>
				{sub(
					Liferay.Language.get(
						'x-project-does-not-meet-the-necessary-resource-requirements-for-this-app-please-contact-sales-support-to-request-additional-resources'
					),
					projectId as string
				)}
			</ProductPurchase.Body>
		);
	}

	if (state === States.IN_PROGRESS) {
		return (
			<ProductPurchase.Body
				title={Liferay.Language.get('installation-in-progress')}
			>
				<ClayLoadingIndicator
					className="mb-4"
					displayType="primary"
					shape="squares"
					size="lg"
				/>

				<span>
					{Liferay.Language.get(
						'the-installation-process-is-ongoing-and-may-take-some-time-navigating-to-other-sections-will-not-cancel-the-process'
					)}
				</span>
			</ProductPurchase.Body>
		);
	}

	if (state === States.CONFIRM_INSTALLATION) {
		return (
			<ProductPurchase.Body
				primaryButtonProps={{
					children: Liferay.Language.get('install'),
					onClick: onClickInstall,
				}}
				secondaryButtonProps={secondaryButtonProps}
				title={Liferay.Language.get('confirmation-required')}
			>
				{Liferay.Language.get(
					'are-you-sure-you-want-to-proceed-with-the-installation-click-install-to-confirm-or-cancel-to-go-back'
				)}
			</ProductPurchase.Body>
		);
	}

	return null;
}
