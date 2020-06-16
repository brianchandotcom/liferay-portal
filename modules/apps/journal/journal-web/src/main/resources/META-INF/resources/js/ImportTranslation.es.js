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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useEffect, useRef, useState} from 'react';

const VALID_EXTENSIONS = '.xliff,.xlf';

export default function ImportTranslation({saveDraftBtnId, submitBtnId}) {
	const [hasError, setHasError] = useState();
	const [importFile, setImporFile] = useState();

	const inputFileRef = useRef();

	useEffect(() => {
		Liferay.Util.toggleDisabled('#' + saveDraftBtnId, !importFile);
		Liferay.Util.toggleDisabled('#' + submitBtnId, !importFile);
	}, [importFile]);

	return (
		<div>
			<h4>{Liferay.Language.get('import-file')}</h4>
			<p>
				<span className="text-secondary">
					{Liferay.Language.get(
						'please-upload-your-translation-file'
					)}
				</span>
			</p>

			<div className="mb-5 mt-4">
				<h4>{Liferay.Language.get('file-upload')}</h4>


				{!importFile && (
					<>
						<ClayButton
							displayType="secondary"
							onClick={(e) => {
								inputFileRef.current.click()
							}}
						>
							{Liferay.Language.get('select-file')}
						</ClayButton>

						<ClayInput
							accept={VALID_EXTENSIONS}
							className="d-none"
							name="import-file"
							onChange={e => {
								setImporFile(e.target.files[0]);
							}}
							ref={inputFileRef}
							type="file"
						/>
					</>
				)}

				{importFile && (
					<>
						<strong>{importFile.name}</strong>

						<ClayButtonWithIcon
							displayType="unstyled"
							onClick={() => {setImporFile(null)}}
							symbol="times-circle"
							title={Liferay.Language.get('delete')}
						/>
					</>
				)}

				{hasError && (
					<ClayForm.FeedbackGroup className="has-error">
						<ClayForm.FeedbackItem>
							<ClayForm.FeedbackIndicator symbol="exclamation-full" />

							<strong>{Liferay.Language.get('error')}: </strong>

							{Liferay.Language.get('the-file-id-doesnt-match')}
						</ClayForm.FeedbackItem>
					</ClayForm.FeedbackGroup>
				)}
			</div>
		</div>
	);
}
