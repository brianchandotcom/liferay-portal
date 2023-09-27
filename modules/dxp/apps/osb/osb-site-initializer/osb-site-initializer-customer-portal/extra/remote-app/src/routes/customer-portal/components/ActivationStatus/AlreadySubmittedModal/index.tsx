/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import Button from '~/common/components/Button';

type AlreadySubmittedFormModalProps = {
	onClose: (state: boolean) => void;
	submittedModalTexts: {
		paragraph: string;
		subtitle: string;
		text: string;
		title: string;
	};
};

const AlreadySubmittedFormModal: React.FC<AlreadySubmittedFormModalProps> = ({
	onClose,
	submittedModalTexts,
}) => (
	<div className="pt-4 px-4">
		<div className="flex-row mb-2">
			<header className="mb-5">
				<h2 className="mb-1 text-neutral-10">
					{submittedModalTexts.title}
				</h2>

				<p className="text-neutral-7 text-paragraph-sm">
					{submittedModalTexts.subtitle}
				</p>
			</header>

			<h5 className="my-1 text-neutral-10">{submittedModalTexts.text}</h5>

			<p className="mb-5 text-neutral-10">
				{submittedModalTexts.paragraph}
			</p>
		</div>

		<div className="d-flex justify-content-center mb-4 mt-5">
			<Button
				className="px-3 py-2"
				displayType="primary"
				onClick={() => onClose(true)}
			>
				{i18n.translate('done')}
			</Button>
		</div>
	</div>
);

export default AlreadySubmittedFormModal;
