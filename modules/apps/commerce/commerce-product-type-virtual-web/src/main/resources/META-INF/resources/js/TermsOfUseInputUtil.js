/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({
    termsOfUseJournalArticleBrowserURL,
    useTermsOfUseJournal,
}) {
    
    Liferay.Util.toggleBoxes(
        `<portlet:namespace />termsOfUseRequired`,
        `<portlet:namespace />termsOfUseSettings`
    );

    const journalArticleNameInput = document.getElementById(
        `<portlet:namespace />journalArticleNameInput`
    );

    const journalArticleRemove = document.getElementById(
        `<portlet:namespace />journalArticleRemove`
    );

    const selectArticle = document.getElementById(
        `<portlet:namespace />selectArticle`
    );

    if (journalArticleNameInput && journalArticleRemove && selectArticle) {
        selectArticle.addEventListener('click', (event) => {
            event.preventDefault();

            Liferay.Util.openSelectionModal({
                onSelect: (selectedItem) => {
                    const termsOfUseJournalArticleResourcePrimKey =
                        document.getElementById(
                            `<portlet:namespace />termsOfUseJournalArticleResourcePrimKey`
                        );

                    const itemValue = JSON.parse(selectedItem.value);

                    if (termsOfUseJournalArticleResourcePrimKey) {
                        termsOfUseJournalArticleResourcePrimKey.value =
                            itemValue.classPK;
                    }

                    journalArticleRemove.classList.remove('hide');

                    journalArticleNameInput.innerText = itemValue.title;
                    if (!Liferay.FeatureFlags['LPD-51378']) {
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
                title: '<liferay-ui:message key="select-web-content" />',
                url: termsOfUseJournalArticleBrowserURL,
            });
        });

        journalArticleRemove.addEventListener('click', (event) => {
            event.preventDefault();

            const termsOfUseJournalArticleResourcePrimKey =
                document.getElementById(
                    `<portlet:namespace />termsOfUseJournalArticleResourcePrimKey`
                );

            if (termsOfUseJournalArticleResourcePrimKey) {
                termsOfUseJournalArticleResourcePrimKey.value = 0;
            }

            journalArticleNameInput.innerText =
                '<liferay-ui:message key="none" />';

            journalArticleRemove.classList.add('hide');
            
            if (!Liferay.FeatureFlags['LPD-51378']) {
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
    if (!Liferay.FeatureFlags['LPD-51378']) {
        document.addEventListener('DOMContentLoaded', () => {
            waitForElement('#termsOfUseContent .ck-editor__top').then(() => {
                evaluateContentVisibility();
            });
        });

        Liferay.on('endNavigate', () => {
            waitForElement('#termsOfUseContent .ck-editor__top').then(() => {
                evaluateContentVisibility();
            });
        });

        const evaluateContentVisibility = () => {
            const ckEditorToolbar = document.querySelector(
                '#termsOfUseContent .ck-editor__top'
            );

            const hasTermsOfUseContent = useTermsOfUseJournal === 'true';

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

