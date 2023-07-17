/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import classNames from 'classnames';
import {
    fetch,
	navigate,
    openToast
} from 'frontend-js-web';
import React, {useState} from 'react';

interface Props {
    backURL: string,
	importURL: string;
	portletNamespace: string;
}

interface ImportResult {
    message: string;
    name: string;
    type: "fragment" | "composition";

}

interface ImportResults {
    imported: ImportResult[];
	"imported-draft": ImportResult[];
	invalid: ImportResult[];
}

function Import({backURL, importURL, portletNamespace}: Props) {
	const [error, setError] = useState<string | null>(null);
	const [overwrite, setOverwrite] = useState<boolean>(true);
    const [file, setFile] = useState<File | null>(null);
    const [importResults, setImportResults] = useState<ImportResults | null>(null)

	const validateFile = (event: React.ChangeEvent<HTMLInputElement>) => {
		if (!event.target.files || event.target.files?.length === 0) {
            setFile(null);

			return;
		}

        setFile(event.target.files[0]);

		const fileName: string = event.target.files[0]?.name || '';

		const fileExtension = fileName
			.substring(fileName.lastIndexOf('.') + 1)
			.toLowerCase();

		if (fileExtension === 'zip') {
			setError(null);
		}
		else {
			setError(Liferay.Language.get('only-zip-files-are-allowed'));
		}
	};

    const goBack = () => {
        navigate(backURL);
    }

    const importFile = () => {
        const formData = new FormData();

        if (!file) {
            return;
        }

		formData.append(`${portletNamespace}file`, file);

		fetch(importURL, {
			body: formData,
			method: 'POST',
		})
        .then((response) => response.json())
        .then(({importResults}) => {
                if (!importResults || !importResults.imported || !importResults["imported-draft"]) {
                    navigate(backURL);
                }
                setImportResults(importResults);

                // Display Import Results
            
            })
			.catch(() => {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			});
    }

	return (!importResults && (
            <>
                <ClayToolbar light>
                    <ClayLayout.ContainerFluid>
                        <ClayToolbar.Nav className="justify-content-sm-end">
                            <ClayToolbar.Item>
                                    <ClayButton displayType="secondary" onClick={goBack} size="sm">{Liferay.Language.get('cancel')}</ClayButton>
                                </ClayToolbar.Item>

                                <ClayToolbar.Item>
                                    <ClayButton disabled={!!error || !file} onClick={importFile} size="sm">{Liferay.Language.get('import')}</ClayButton>
                                </ClayToolbar.Item>
                            </ClayToolbar.Nav>
                    </ClayLayout.ContainerFluid>
                </ClayToolbar>
        
                <ClayLayout.ContainerFluid view>
                    <ClayLayout.Sheet size='lg'>
                        <h2 className="c-mb-4 text-6">{Liferay.Language.get('import-file')}</h2>

                        <p>
                            {Liferay.Language.get(
                                'select-a-zip-file-containing-one-or-multiple-entries'
                            )}
                        </p>

                        <ClayForm.Group
                            className={classNames({'has-error': error})}
                        >
                            <label htmlFor={`${portletNamespace}file`}>
                                {Liferay.Language.get('file')}

                                <ClayIcon
                                    className="reference-mark"
                                    symbol="asterisk"
                                />
                            </label>

                            <ClayInput
                                data-testid={`${portletNamespace}file`}
                                id={`${portletNamespace}file`}
                                onChange={validateFile}
                                type="file"
                            />

                            {error && (
                                <ClayForm.FeedbackGroup>
                                    <ClayForm.FeedbackItem>
                                        <ClayForm.FeedbackIndicator symbol="exclamation-full" />

                                        {error}
                                    </ClayForm.FeedbackItem>
                                </ClayForm.FeedbackGroup>
                            )}
                        </ClayForm.Group>

                        <ClayCheckbox
                            checked={overwrite}
                            data-testid={`${portletNamespace}overwrite`}
                            label={Liferay.Language.get(
                                'overwrite-existing-entries'
                            )}
                            onChange={() => setOverwrite((val) => !val)}
                        />
                    </ClayLayout.Sheet>
                </ClayLayout.ContainerFluid>
            </>
        )
	);
}

export default Import;
