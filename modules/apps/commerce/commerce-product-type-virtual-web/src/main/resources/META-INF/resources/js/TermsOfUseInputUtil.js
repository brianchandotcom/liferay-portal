/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({
	noneText,
	portletNamespace,
	selectWebContent,
    termsOfUseJournalArticleBrowserURL,
    useTermsOfUseJournal,
}) {

    Liferay.Util.toggleBoxes(
        `${portletNamespace}termsOfUseRequired`,
        `${portletNamespace}termsOfUseSettings`
    );

    const journalArticleNameInput = document.getElementById(
        `${portletNamespace}journalArticleNameInput`
    );

    const journalArticleRemove = document.getElementById(
        `${portletNamespace}journalArticleRemove`
    );

    const selectArticle = document.getElementById(
        `${portletNamespace}selectArticle`
    );

    if (journalArticleNameInput && journalArticleRemove && selectArticle) {
        selectArticle.addEventListener('click', (event) => {
            event.preventDefault();

            Liferay.Util.openSelectionModal({
                onSelect: (selectedItem) => {
                    const termsOfUseJournalArticleResourcePrimKey =
                        document.getElementById(
                            `${portletNamespace}termsOfUseJournalArticleResourcePrimKey`
                        );

                    const itemValue = JSON.parse(selectedItem.value);

                    if (termsOfUseJournalArticleResourcePrimKey) {
                        termsOfUseJournalArticleResourcePrimKey.value =
                            itemValue.classPK;
                    }

                    journalArticleRemove.classList.remove('hide');

                    journalArticleNameInput.innerText = itemValue.title;
                    if (Liferay.FeatureFlags['LPD-11235']) {
                        const ckEditorToolbar = document.querySelector(
                            '#termsOfUseContent .ck-editor__top'
                        );
                        ckEditorToolbar.style.display = 'none';
    
                        const ckEditorContent = document.querySelector(
                            '#termsOfUseContent .ck-editor__main .ck-content'
                        );
                        ckEditorContent.classList.add('ck-read-only');
                        ckEditorContent.setAttribute('contenteditable', false);

                    }
                },
                selectEventName: 'selectJournalArticle',
                title: selectWebContent,
                url: termsOfUseJournalArticleBrowserURL,
            });
        });

        journalArticleRemove.addEventListener('click', (event) => {
            event.preventDefault();

            const termsOfUseJournalArticleResourcePrimKey =
                document.getElementById(
                    `${portletNamespace}termsOfUseJournalArticleResourcePrimKey`
                );

            if (termsOfUseJournalArticleResourcePrimKey) {
                termsOfUseJournalArticleResourcePrimKey.value = 0;
            }

            journalArticleNameInput.innerText =
                noneText;

            journalArticleRemove.classList.add('hide');
            
            if (Liferay.FeatureFlags['LPD-11235']) {
                const ckEditorToolbar = document.querySelector(
                    '#termsOfUseContent .ck-editor__top'
                );
                ckEditorToolbar.style.display = 'block';

                const ckEditorContent = document.querySelector(
                    '#termsOfUseContent .ck-editor__main .ck-content'
                );
                ckEditorContent.classList.remove('ck-read-only');
                ckEditorContent.setAttribute('contenteditable', true);
            }
        });
    }
    if (Liferay.FeatureFlags['LPD-11235']) {
        window.onload = () => {
            waitForElement('#termsOfUseContent .ck-editor__top').then(() => {
                evaluateContentVisibility();
            });
        };

        const evaluateContentVisibility = () => {
            const ckEditorToolbar = document.querySelector(
                '#termsOfUseContent .ck-editor__top'
            );

            const hasTermsOfUseContent = useTermsOfUseJournal === true;

            if (hasTermsOfUseContent) {
                ckEditorToolbar.style.display = 'none';
                const ckEditorContent = document.querySelector(
                    '#termsOfUseContent .ck-editor__main .ck-content'
                );
                ckEditorContent.classList.add('ck-read-only');
                ckEditorContent.setAttribute('contenteditable', false);
            }
            else {
                ckEditorToolbar.style.display = 'block';
                const ckEditorContent = document.querySelector(
                    '#termsOfUseContent .ck-editor__main .ck-content'
                );
                ckEditorContent.classList.remove('ck-read-only');
                ckEditorContent.setAttribute('contenteditable', true);
            }
        };

        const waitForElement = (selector) => {
            return new Promise((resolve) => {
                const selectedElement = document.querySelector(selector);
                if (selectedElement) {
                    return resolve(selectedElement);
                }

                const observer = new MutationObserver(() => {
                    const pendingElement = document.querySelector(selector);
                    if (pendingElement) {
                        observer.disconnect();
                        resolve(pendingElement);
                    }
                });

                observer.observe(document.body, {childList: true, subtree: true});
            });
        };
    }
}

