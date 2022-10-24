@charset "UTF-8";
/**
 * Clay 3.73.0
 *
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-FileCopyrightText: © 2020 Contributors to the project Clay <https://github.com/liferay/clay/graphs/contributors>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
/**
 * Bootstrap v4.4.1
 *
 * SPDX-FileCopyrightText: © 2019 Twitter, Inc. <https://twitter.com>
 * SPDX-FileCopyrightText: © 2019 The Bootstrap Authors <https://getbootstrap.com/>
 *
 * SPDX-License-Identifier: LicenseRef-MIT-Bootstrap
 */
/**
 * Clay 3.73.0
 *
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-FileCopyrightText: © 2020 Contributors to the project Clay <https://github.com/liferay/clay/graphs/contributors>
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
/**
 * Bootstrap v4.4.1
 *
 * SPDX-FileCopyrightText: © 2019 Twitter, Inc. <https://twitter.com>
 * SPDX-FileCopyrightText: © 2019 The Bootstrap Authors <https://getbootstrap.com/>
 *
 * SPDX-License-Identifier: LicenseRef-MIT-Bootstrap
 */
.sr-only {
border: 0;
clip: rect(0, 0, 0, 0);
height: 1px;
margin: -1px;
overflow: hidden;
padding: 0;
position: absolute;
white-space: nowrap;
width: 1px;
}

.sr-only-focusable:active, .sr-only-focusable:focus {
clip: auto;
height: auto;
overflow: visible;
position: static;
white-space: normal;
width: auto;
}

.lfr-asset-column-details .lfr-asset-categories {
color: #7d7d7d;
}
.lfr-asset-column-details .lfr-asset-categories .lfr-asset-category {
color: #555;
}
.lfr-asset-column-details .lfr-asset-description {
color: #444;
font-style: italic;
margin: 0.5em auto 2em;
-ms-hyphens: none;
-webkit-hyphens: none;
hyphens: none;
}
.lfr-asset-column-details .lfr-asset-icon {
border-right: 1px solid transparent;
color: #999;
float: left;
line-height: 1em;
margin-right: 10px;
padding-right: 10px;
}
.lfr-asset-column-details .lfr-asset-icon.last {
border-width: 0;
}
.lfr-asset-column-details .lfr-asset-tags {
color: #7d7d7d;
}
.lfr-asset-column-details .lfr-asset-tags .tag {
color: #555;
}
.lfr-asset-column-details .lfr-asset-metadata {
clear: both;
margin-bottom: 1.5em;
padding-bottom: 1.5em;
}
.lfr-asset-column-details .lfr-panel {
clear: both;
margin-bottom: 0.2em;
}
.lfr-asset-column-details .lfr-panel.lfr-extended {
border: 1px solid #ccc;
margin-bottom: 0;
}
.lfr-asset-column-details .taglib-custom-attributes-list label,
.lfr-asset-column-details .lfr-asset-field label {
display: block;
}
.lfr-asset-column-details .taglib-custom-attributes-list {
margin-bottom: 1em;
}

.overlay {
position: absolute;
}

.overlay-hidden,
.overlaymask-hidden {
display: none;
}

.lfr-form-row {
border-bottom: 1px solid #ccc;
margin-bottom: 10px;
margin-top: 10px;
padding: 1px 5px 25px;
position: relative;
}
.lfr-form-row:after {
clear: both;
content: "";
display: block;
height: 0;
}
.lfr-form-row:hover {
border: solid #ccc;
border-width: 1px 0;
padding-top: 0;
}
.lfr-form-row.lfr-form-row-inline .form-group {
margin-right: 5px;
}
.lfr-form-row legend .field-label {
float: left;
margin-right: 10px;
}
.lfr-form-row .handle-sort-vertical {
background: url(../images/application/handle_sort_vertical.png) no-repeat 0 50%;
cursor: move;
padding-left: 20px;
}
.lfr-form-row fieldset {
border-width: 0;
margin: 0;
padding: 0;
}

.lfr-autorow-controls {
bottom: 5px;
position: absolute;
right: 5px;
}

.datepicker-popover-content .popover-content {
padding: 0.75rem 1rem;
}

.dropdown.open > .dropdown-menu, .overlay-content .open > .dropdown-menu {
display: block;
}

.form-control-inline {
background-color: transparent;
font-size: 1.125rem;
font-weight: 700;
}
.form-control-inline:not(:hover) {
border-color: transparent;
}
.form-control-inline::placeholder {
color: #a9afb5;
font-style: italic;
}

.form-search .input-group {
position: relative;
}
.form-search .input-group.advanced-search .search-query {
padding-left: 40px;
z-index: 0;
}
.form-search .input-group.advanced-search .toggle-advanced {
cursor: pointer;
left: 0;
line-height: 1;
padding: 8px 10px;
position: absolute;
top: 0;
}
.form-search .input-group.advanced-search .toggle-advanced .caret {
margin-top: 7px;
}
.form-search .input-group.advanced-search .toggle-advanced .icon-search {
font-size: 14px;
}
@media (max-width: 991.98px) {
.form-search .input-group {
	width: 100%;
}
.form-search .input-group .btn {
	clip: rect(0, 0, 0, 0);
	left: -9999px;
	position: absolute;
}
.form-search .input-group input.search-query {
	width: 100%;
}
}

.lfr-table {
border-collapse: collapse;
clear: both;
}
.lfr-table > tbody > tr > td,
.lfr-table > tbody > tr > th,
.lfr-table > thead > tr > td,
.lfr-table > thead > tr > th,
.lfr-table > tfoot > tr > td,
.lfr-table > tfoot > tr > th {
padding: 0 5px;
overflow-wrap: break-all;
word-wrap: break-all;
}
.lfr-table > tbody > tr > td:first-child, .lfr-table > tbody > tr > td.first-child,
.lfr-table > tbody > tr > th:first-child,
.lfr-table > tbody > tr > th.first-child,
.lfr-table > thead > tr > td:first-child,
.lfr-table > thead > tr > td.first-child,
.lfr-table > thead > tr > th:first-child,
.lfr-table > thead > tr > th.first-child,
.lfr-table > tfoot > tr > td:first-child,
.lfr-table > tfoot > tr > td.first-child,
.lfr-table > tfoot > tr > th:first-child,
.lfr-table > tfoot > tr > th.first-child {
padding-left: 0;
}
.lfr-table > tbody > tr > td:last-child, .lfr-table > tbody > tr > td.last-child,
.lfr-table > tbody > tr > th:last-child,
.lfr-table > tbody > tr > th.last-child,
.lfr-table > thead > tr > td:last-child,
.lfr-table > thead > tr > td.last-child,
.lfr-table > thead > tr > th:last-child,
.lfr-table > thead > tr > th.last-child,
.lfr-table > tfoot > tr > td:last-child,
.lfr-table > tfoot > tr > td.last-child,
.lfr-table > tfoot > tr > th:last-child,
.lfr-table > tfoot > tr > th.last-child {
padding-right: 0;
}
.lfr-table > tbody > tr > th,
.lfr-table > thead > tr > th,
.lfr-table > tfoot > tr > th {
font-weight: bold;
}

.lfr-pagination:after {
clear: both;
content: "";
display: block;
height: 0;
visibility: hidden;
}
.lfr-pagination .dropdown-menu {
display: none;
}
.lfr-pagination .dropdown-toggle .icon-caret-down {
margin-left: 5px;
}
.lfr-pagination .lfr-pagination-controls {
float: left;
}
.lfr-pagination .lfr-pagination-controls .search-results {
display: inline-block;
margin-left: 10px;
margin-top: 5px;
top: 10px;
}
.lfr-pagination .lfr-pagination-controls li .lfr-pagination-link {
border-width: 0;
clear: both;
color: #333;
display: block;
font-weight: normal;
line-height: 20px;
padding: 3px 20px;
text-align: left;
white-space: nowrap;
width: 100%;
}
.lfr-pagination .lfr-pagination-delta-selector {
float: left;
}
@media (max-width: 991.98px) {
.lfr-pagination .lfr-pagination-delta-selector .lfr-icon-menu-text {
	display: none;
}
}
.lfr-pagination .pagination-content {
float: right;
}

.lfr-panel.lfr-extended {
border: 1px solid transparent;
border-color: #dedede #bfbfbf #bfbfbf #dedede;
}
.lfr-panel.lfr-extended.lfr-collapsible .lfr-panel-button {
display: block;
}
.lfr-panel.lfr-extended.lfr-collapsed .lfr-panel-titlebar {
border-bottom-width: 0;
}
.lfr-panel.panel-default .panel-heading {
border-bottom-width: 0;
}
.lfr-panel .toggler-header {
cursor: pointer;
}

.sidebar-sm {
font-size: 0.875rem;
}
.sidebar-sm .sheet-subtitle {
font-size: 0.75rem;
margin-bottom: 1rem;
}
.sidebar-sm .form-group {
margin-bottom: 1rem;
}
.sidebar-sm .form-control {
border-radius: 0.1875rem;
font-size: 0.875rem;
height: 2rem;
line-height: 1.5;
min-height: auto;
padding: 0.25rem 0.75rem;
}
.sidebar-sm .form-control.form-control-tag-group {
height: auto;
}
.sidebar-sm .form-control.form-control-tag-group .form-control-inset {
margin-bottom: 0;
margin-top: 0;
}
.sidebar-sm .form-control.form-control-tag-group .label {
margin-bottom: 0;
margin-top: 0;
padding: 0 0.25rem;
}
.sidebar-sm .form-control[type=file] {
padding: 0;
}
.sidebar-sm select.form-control {
padding-right: 1.6rem;
}
.sidebar-sm select.form-control:not([size]) {
height: 2rem;
}
.sidebar-sm .article-content-description .input-localized.input-localized-editor .input-group-item .wrapper .form-control {
min-height: auto;
padding: 0.285rem 0.75rem;
}
.sidebar-sm .btn:not(.btn-unstyled) {
border-radius: 0.1875rem;
font-size: 0.875rem;
line-height: 1.15;
padding: 0.4375rem 0.75rem;
}
.sidebar-sm .btn:not(.btn-unstyled).close {
padding: 0.4375rem;
}
.sidebar-sm .btn.btn-monospaced, .sidebar-sm .btn.btn-monospaced.btn-sm {
height: 2rem;
padding: 0.1875rem 0;
width: 2rem;
}
.sidebar-sm .btn.btn-monospaced.input-localized-trigger, .sidebar-sm .btn.btn-monospaced.btn-sm.input-localized-trigger {
padding: 0;
}
.sidebar-sm .input-group-item .input-group-text {
font-size: 0.875rem;
height: 2rem;
min-width: 2rem;
padding-left: 0.75rem;
padding-right: 0.75rem;
}
.sidebar-sm .input-group-item .btn .btn-section {
font-size: 0.5625rem;
}
.sidebar-sm .list-group-item-flex {
padding: 0.5rem 0.25rem;
}
.sidebar-sm .list-group-item-flex .autofit-col {
padding-left: 0.25rem;
padding-right: 0.25rem;
}

.lfr-translation-manager {
border-radius: 4px;
display: inline-block;
margin-top: 0.5em;
min-height: 1.8em;
}
.lfr-translation-manager .lfr-translation-manager-content .lfr-translation-manager-default-locale {
display: inline-block;
width: auto;
}
.lfr-translation-manager .lfr-translation-manager-content .lfr-translation-manager-icon-menu {
float: none;
padding: 0.4em 0;
}
.lfr-translation-manager .lfr-translation-manager-content .lfr-translation-manager-icon-menu li,
.lfr-translation-manager .lfr-translation-manager-content .lfr-translation-manager-icon-menu li strong {
display: inline;
}
.lfr-translation-manager .lfr-translation-manager-content .lfr-translation-manager-translations-message {
margin: 10px 0;
}
.lfr-translation-manager .lfr-translation-manager-available-translations {
white-space: normal;
}
.lfr-translation-manager .lfr-translation-manager-available-translations .lfr-translation-manager-available-translations-links {
line-height: 1;
}
.lfr-translation-manager .lfr-translation-manager-translation {
border: 1px solid transparent;
border-radius: 4px;
cursor: pointer;
display: inline-block;
margin: 0.2em;
padding: 0.4em 0.3em 0.4em 0.5em;
text-decoration: none;
}
.lfr-translation-manager .lfr-translation-manager-translation * {
vertical-align: middle;
}
.lfr-translation-manager .lfr-translation-manager-translation img {
margin-right: 0.3em;
}
.lfr-translation-manager .lfr-translation-manager-translation:hover {
background-color: #d1e5ef;
}
.lfr-translation-manager .lfr-translation-manager-translation.lfr-translation-manager-translation-editing {
background-color: #598bec;
border-color: #224fa8;
color: #fff;
}
.lfr-translation-manager .lfr-translation-manager-change-default-locale {
margin: 0 0.4em;
}
.lfr-translation-manager .lfr-translation-manager-delete-translation {
display: inline-block;
padding: 0 2px;
}
.lfr-translation-manager .lfr-translation-manager-delete-translation svg {
pointer-events: none;
}

.lfr-tree a {
text-decoration: none;
}
.lfr-tree li {
margin-bottom: 2px;
padding-left: 0;
}
.lfr-tree li ul li, .lfr-tree li.tree-item {
padding-left: 0;
}
.lfr-tree li.tree-item {
padding-left: 5px;
}
.lfr-tree li.tree-item li {
padding-left: 20px;
}
.lfr-tree li.tree-item ul {
margin-left: 0;
margin-top: 5px;
}

.lfr-upload-container {
margin-bottom: 1rem;
}
.lfr-upload-container .upload-target {
border: 3px dashed #e5e7e9;
margin-bottom: 1rem;
min-height: 2rem;
padding: 2rem 0;
position: relative;
text-align: center;
}
.upload-drop-intent .lfr-upload-container .upload-target {
z-index: 100;
}
.upload-drop-active .lfr-upload-container .upload-target {
background-color: #92e5a5;
border-color: #69db83;
}
.lfr-upload-container .upload-target .drop-file-text {
font-weight: normal;
}
.mobile .lfr-upload-container .upload-target .drop-file-text {
display: none;
}
.lfr-upload-container .upload-target .small {
display: block;
margin: 5px 0;
text-transform: lowercase;
}
.lfr-upload-container .manage-upload-target {
padding-top: 5px;
position: relative;
}
.lfr-upload-container .manage-upload-target .select-files {
float: left;
line-height: 0;
margin: 0 1.125rem 1.125rem;
padding: 0 0 0 5px;
}
.lfr-upload-container .cancel-uploads,
.lfr-upload-container .clear-uploads {
background-repeat: no-repeat;
float: right;
}
.lfr-upload-container .cancel-uploads {
background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAnFBMVEX////4YWP/dXjyS07/dXj9bXD6a234YWP4XWD2WVv2VFfsOTzoLzHmKSvkISP2VFf0TE/vREftPT/iHB72WVvvREf0TE//hon/gYX/fYD/e33/dXj/cXP9bXD/a236a23/Zmb4YWP4XWD/Wl32WVv/VVj2VFf3VFb0TE/yS072SUvvREfuQELtPT/sOTzrMzXoLzHnLC/mKSvkISPh2jkWAAAAF3RSTlMAESIiMzMzMzMzMzMzMzNERERERHd3qtw8lzkAAACOSURBVHjaZcjZDsIgEIXhcd+tu5YBKUixttS6vP+7OQKJTfxvTr4D7Tpxu/2w410SjDjwVvLQ805TPiRryfQeKM6OTI68K/BJPHGlJZJjSqSMofEOGXbJecsTgzkds58V5+J8refBU7Jx9yIrmkW0sA6gqbLyuaRjZZWtgXq58rEFan0jf3uTfRuIkf/7AO8DDcVPSSG3AAAAAElFTkSuQmCC);
margin-right: 0;
}
.lfr-upload-container .clear-uploads {
background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAdVBMVEX///+6QwTEVx65RQPEVx3EWB64RQPDVxq5RAO5RAO5RQPATQ65RQO5RQO5RQPRczjJZCW9TQTLay28QgTdZAHhaAPmdyHqeQXrikDudgDvfxj0nWP1qHL2tY33tY34dgD+za7+1rj/dxH/fxj/hiH/kk3///9TM3sUAAAAFHRSTlMAbXd4eHh6gN3e3+Hi4+T5+/z8/mIsq5IAAABHSURBVBjTY2CgGWDlFuZhRuIz8cspyggxIgQ4ZFWVVCQ4EQLCCmpqalLCCAFeaWV5SVE+hAC7gJi4iCAbkqksXMK8bNRzNADCOQN++eLhCQAAAABJRU5ErkJggg==);
padding-left: 16px;
}
.lfr-upload-container .upload-file.upload-complete.file-saved {
padding-left: 25px;
}
.lfr-upload-container .upload-file .file-title {
display: inline-block;
max-width: 95%;
overflow: hidden;
padding-right: 16px;
text-overflow: ellipsis;
vertical-align: middle;
white-space: nowrap;
}
.lfr-upload-container .upload-file .icon-file {
font-size: 40px;
}
.lfr-upload-container .upload-list-info {
margin: 1em 0 0.5em;
}
.lfr-upload-container .upload-list-info h4 {
font-size: 1.3em;
}
.lfr-upload-container .cancel-button {
color: #6c757d;
margin-top: 1px;
position: absolute;
right: 5px;
top: 50%;
white-space: nowrap;
}
.lfr-upload-container .cancel-button .cancel-button-text {
display: none;
margin-left: 5px;
}
.lfr-upload-container .cancel-button:hover .cancel-button-text {
display: inline;
}
.lfr-upload-container .cancel-button .lexicon-icon {
height: 12px;
}
.lfr-upload-container .delete-button {
color: #6c757d;
}
.lfr-upload-container .delete-button-col {
padding-right: 10px;
}
.lfr-upload-container .file-added .success-message {
float: right;
font-weight: normal;
}
.lfr-upload-container .upload-error {
opacity: 1;
padding-left: 25px;
}
.lfr-upload-container .upload-complete .cancel-button,
.lfr-upload-container .delete-button,
.lfr-upload-container .upload-complete.file-saved .delete-button,
.lfr-upload-container .upload-complete.upload-error .delete-button {
display: none;
}
.lfr-upload-container .multiple-files .upload-error {
background: #f5c4c9 url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAYAAAAf8/9hAAAABGdBTUEAAK/INwWK6QAAABl0RVh0U29mdHdhcmUAQWRvYmUgSW1hZ2VSZWFkeXHJZTwAAAJPSURBVDjLpZPLS5RhFMYfv9QJlelTQZwRb2OKlKuINuHGLlBEBEOLxAu46oL0F0QQFdWizUCrWnjBaDHgThCMoiKkhUONTqmjmDp2GZ0UnWbmfc/ztrC+GbM2dXbv4ZzfeQ7vefKMMfifyP89IbevNNCYdkN2kawkCZKfSPZTOGTf6Y/m1uflKlC3LvsNTWArr9BT2LAf+W73dn5jHclIBFZyfYWU3or7T4K7AJmbl/yG7EtX1BQXNTVCYgtgbAEAYHlqYHlrsTEVQWr63RZFuqsfDAcdQPrGRR/JF5nKGm9xUxMyr0YBAEXXHgIANq/3ADQobD2J9fAkNiMTMSFb9z8ambMAQER3JC1XttkYGGZXoyZEGyTHRuBuPgBTUu7VSnUAgAUAWutOV2MjZGkehgYUA6O5A0AlkAyRnotiX3MLlFKduYCqAtuGXpyH0XQmOj+TIURt51OzURTYZdBKV2UBSsOIcRp/TVTT4ewK6idECAihtUKOArWcjq/B8tQ6UkUR31+OYXP4sTOdisivrkMyHodWejlXwcC38Fvs8dY5xaIId89VlJy7ACpCNCFCuOp8+BJ6A631gANQSg1mVmOxxGQYRW2nHMha4B5WA3chsv22T5/B13AIicWZmNZ6cMchTXUe81Okzz54pLi0uQWp+TmkZqMwxsBV74Or3od4OISPr0e3SHa3PX0f3HXKofNH/UIG9pZ5PeUth+CyS2EMkEqs4fPEOBJLsyske48/+xD8oxcAYPzs4QaS7RR2kbLTTOTQieczfzfTv8QPldGvTGoF6/8AAAAASUVORK5CYII=) no-repeat 5px 5px;
border-color: #bd2130;
color: #bd2130;
font-weight: normal;
margin-bottom: 16px;
padding: 8px 8px 8px 24px;
}
.lfr-upload-container .multiple-files .upload-error .error-message {
display: block;
}
.lfr-upload-container .multiple-files .upload-complete.file-saved .form-check-middle-left .card-body,
.lfr-upload-container .multiple-files .upload-complete.upload-error .form-check-middle-left .card-body {
padding-left: 1rem;
}
.lfr-upload-container .multiple-files .upload-complete.file-saved .form-check-middle-left .custom-control-label,
.lfr-upload-container .multiple-files .upload-complete.upload-error .form-check-middle-left .custom-control-label {
display: none;
}
.lfr-upload-container .single-file .upload-error {
list-style: none;
margin-top: 1em;
}
.lfr-upload-container .single-file .upload-error .upload-error-message {
margin-bottom: 0.5em;
}
.lfr-upload-container .upload-complete {
padding-left: 5px;
}
.lfr-upload-container .upload-complete .error-message,
.lfr-upload-container .upload-complete .success-message {
font-weight: bold;
margin-left: 1em;
}
.lfr-upload-container .upload-complete .delete-button {
display: inline-block;
}
.lfr-upload-container .upload-complete .select-file:disabled + .custom-control-label {
display: none;
}
.lfr-upload-container .progress {
display: none;
margin-top: 0.5rem;
}
.lfr-upload-container .file-uploading .progress {
display: flex;
}

.lfr-alert-container {
left: 0;
position: absolute;
right: auto;
top: auto;
width: 100%;
z-index: 430;
}
.lfr-alert-container.inline-alert-container {
position: relative;
}
.lfr-alert-container.inline-alert-container .lfr-alert-wrapper {
padding: 0;
}
.lfr-alert-container .lfr-alert-wrapper {
margin-bottom: 5px;
overflow: hidden;
}

.lfr-search-container td .overlay.entry-action {
position: static;
}
.lfr-search-container td .overlay.entry-action .btn a {
display: block;
padding: 4px 12px;
}

.contextual-sidebar {
height: calc(100vh - 49px);
pointer-events: none;
position: fixed;
right: 0;
top: 49px;
transform: translateX(100%);
transition: transform ease 0.5s;
width: 320px;
will-change: transform;
}
.contextual-sidebar.contextual-sidebar-visible {
pointer-events: auto;
transform: translateX(0);
}
body.has-control-menu .contextual-sidebar {
height: calc(100vh - 98px);
top: 98px;
}

@media (min-width: 576px) {
.contextual-sidebar {
	height: calc(100vh - 64px);
	top: 64px;
}
body.has-control-menu .contextual-sidebar {
	height: calc(
				100vh - 120px
			);
	top: 120px;
}

.contextual-sidebar-content {
	transition: padding ease 0.5s;
	will-change: padding;
}

.contextual-sidebar-visible .contextual-sidebar-content,
.contextual-sidebar-visible + .contextual-sidebar-content {
	padding-right: 320px;
}
}
.taglib-diff-html div.diff-removed-image {
background: #fdc6c6 url(../images/diff/minus.png);
}
.taglib-diff-html div.diff-added-image {
background: #cfc url(../images/diff/plus.png) no-repeat;
}
.taglib-diff-html div.diff-removed-image, .taglib-diff-html div.diff-added-image {
height: 300px;
margin: 2px;
opacity: 0.55;
position: absolute;
width: 200px;
}
.taglib-diff-html span.diff-html-added {
background-color: #cfc;
font-size: 1em;
}
.taglib-diff-html span.diff-html-added img {
border: 2px solid #cfc;
}
.taglib-diff-html span.diff-html-changed {
background: url(../images/diff/underline.png) bottom repeat-x;
}
.taglib-diff-html span.diff-html-changed img {
border: 2px dotted #009;
}
.taglib-diff-html span.diff-html-removed {
background-color: #fdc6c6;
font-size: 1em;
text-decoration: line-through;
}
.taglib-diff-html span.diff-html-removed img {
border: 2px solid #fdc6c6;
}

.taglib-discussion {
font-size: 0.875rem;
margin-top: 1rem;
}
.taglib-discussion .actions-menu .component-action {
display: inline-flex;
}
.taglib-discussion .button-holder {
margin: 1rem 0;
}
.taglib-discussion .comment-container {
margin-bottom: 1.5rem;
}
.taglib-discussion .lfr-discussion .lfr-discussion-form-edit .alloy-editor-placeholder {
border-left-color: #dbdde1;
}
.taglib-discussion .lfr-discussion-body {
font-size: 1rem;
}
@media (min-width: 576px) {
.taglib-discussion .lfr-discussion-body {
	padding-left: 3.5rem;
}
}
@media (min-width: 576px) {
.taglib-discussion .lfr-discussion-details .taglib-user-display {
	word-wrap: break-word;
}
}
.taglib-discussion .lfr-discussion-details .taglib-user-display .user-details .user-name {
font-weight: bold;
text-decoration: none;
}
.taglib-discussion .lfr-discussion-details .taglib-user-display .user-details .user-name:hover {
text-decoration: underline;
}
.taglib-discussion .lfr-discussion-details .taglib-user-display .user-profile-image .avatar {
background-size: 50px;
}
.taglib-discussion .lfr-discussion-container {
border-bottom: 1px solid #e7e7ed;
margin-bottom: 1.5rem;
}
.taglib-discussion .lfr-discussion-container:first-of-type {
border-top: 1px solid #e7e7ed;
padding-top: 1.5rem;
}
.taglib-discussion .lfr-discussion-container:last-of-type {
border-bottom: 0;
}
.taglib-discussion .lfr-discussion-container .lfr-discussion {
padding-left: 1rem;
}
.taglib-discussion .lfr-discussion-container .lfr-discussion .lfr-discussion .lfr-discussion .lfr-discussion {
padding-left: 0;
}
@media (min-width: 576px) {
.taglib-discussion .lfr-discussion-container .lfr-discussion {
	padding-left: 3.5rem;
}
}
.taglib-discussion .lfr-discussion-parent-link {
color: #67678c;
font-weight: 600;
margin-left: 1em;
}
@media (max-width: 767.98px) {
.taglib-discussion .lfr-discussion-parent-link {
	display: block;
	margin-left: 0;
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
}
.taglib-discussion .lfr-discussion-controls .autofit-col:first-of-type > .btn-outline-borderless {
margin-left: -0.75rem;
}
.taglib-discussion .lfr-discussion-more-comments {
border-top: 1px solid #e7e7ed;
margin-top: 1.5rem;
padding-top: 1.5rem;
text-align: center;
}
.taglib-discussion .username {
color: #272833;
font-weight: 600;
}
.taglib-discussion .taglib-workflow-status {
margin-left: 0.5rem;
}
.taglib-discussion .workflow-value {
text-transform: uppercase;
}
@media (min-width: 576px) {
.taglib-discussion .actions-menu {
	display: none;
}
.taglib-discussion .comment-container:hover .actions-menu,
.taglib-discussion .actions-menu:focus,
.taglib-discussion .actions-menu.open {
	display: inline-block;
}
}

.drop-zone {
min-height: 80px;
padding: 20px;
text-align: center;
}
.drop-zone.drop-enabled {
outline: 2px dashed transparent;
}
.drop-zone.drop-enabled:not(.no-border) {
outline-color: rgba(176, 180, 187, 0.5);
}
.drop-zone p {
margin: 10px auto 0;
max-width: 75%;
}

.drop-here-info {
font-size: 25px;
height: 100%;
left: 0;
min-height: 100px;
opacity: 0;
position: absolute;
text-align: center;
top: 0;
visibility: hidden;
width: 100%;
transition: all 0.2s ease-in-out;
}
@media (prefers-reduced-motion: reduce) {
.drop-here-info {
	transition: none;
}
}
.drop-here-info .drop-here-indicator {
height: 115px;
left: 0;
margin: auto;
position: absolute;
right: 0;
top: 50%;
transform: translate(0, -50%);
}
.drop-here-info .drop-icons {
display: inline-block;
position: relative;
width: 160px;
transition: all 0.2s ease-in-out;
}
@media (prefers-reduced-motion: reduce) {
.drop-here-info .drop-icons {
	transition: none;
}
}
.drop-here-info .drop-icons span {
border-radius: 3px;
position: absolute;
text-align: center;
transition: all 0.3s ease-in-out;
}
@media (prefers-reduced-motion: reduce) {
.drop-here-info .drop-icons span {
	transition: none;
}
}
.drop-here-info .drop-icons span:nth-of-type(1) {
box-shadow: 5px 4px 0 0 rgba(0, 0, 0, 0.08);
height: 60px;
left: 0;
line-height: 60px;
opacity: 0;
top: 10px;
transform: rotate(25deg);
width: 60px;
}
.drop-here-info .drop-icons span:nth-of-type(2) {
background: #00c2ff;
color: #fff;
height: 80px;
left: 40px;
line-height: 80px;
width: 80px;
z-index: 2;
}
.drop-here-info .drop-icons span:nth-of-type(3) {
box-shadow: -5px 4px 0 0 rgba(0, 0, 0, 0.08);
display: block;
font-size: 45px;
height: 70px;
left: 90px;
line-height: 70px;
opacity: 0;
position: relative;
top: 8px;
transform: rotate(-25deg);
width: 70px;
z-index: 1;
}
.drop-here-info .drop-icons span:nth-of-type(1), .drop-here-info .drop-icons span:nth-of-type(3) {
background: #e8e6e8;
color: #a5a6ac;
}
.drop-here-info .drop-text {
color: #fff;
display: block;
font-size: 20px;
margin-left: 0;
margin-top: 20px;
}
.drop-active .drop-here-info {
background-color: rgba(0, 0, 0, 0.6);
opacity: 1;
visibility: visible;
z-index: 100;
}
.drop-active .drop-here-info .drop-icons span:nth-of-type(1), .drop-active .drop-here-info .drop-icons span:nth-of-type(3) {
opacity: 1;
}
.drop-active .drop-here-info span:nth-of-type(1) {
transform: rotate(-20deg);
}
.drop-active .drop-here-info span:nth-of-type(2) {
transform: scale(1);
}
.drop-active .drop-here-info span:nth-of-type(3) {
transform: rotate(15deg);
}

.taglib-empty-result-message.sheet {
margin-bottom: 24px;
}
.taglib-empty-result-message .taglib-empty-result-message-description {
color: #6c757d;
margin-top: 8px;
}
.taglib-empty-result-message .taglib-empty-result-message-header,
.taglib-empty-result-message .taglib-empty-result-message-header-has-plus-btn,
.taglib-empty-result-message .taglib-empty-search-result-message-header {
background-position: center;
background-repeat: no-repeat;
background-size: 100%;
margin-left: auto;
margin-right: auto;
}
.taglib-empty-result-message .taglib-empty-result-message-header,
.taglib-empty-result-message .taglib-empty-result-message-header-has-plus-btn {
max-width: 200px;
padding-top: 250px;
}
.taglib-empty-result-message .taglib-empty-result-message-header,
.taglib-empty-result-message .taglib-empty-result-message-header-has-plus-btn {
background-image: url(@theme_image_path@/states/empty_state.gif);
}
.taglib-empty-result-message .taglib-empty-search-result-message-header {
background-image: url(@theme_image_path@/states/search_state.gif);
max-width: 210px;
padding-top: 232px;
}
.taglib-empty-result-message .taglib-empty-state {
background-image: url(@theme_image_path@/states/empty_state.gif);
margin: auto;
max-width: 250px;
padding-top: 250px;
}
.taglib-empty-result-message .taglib-success-state {
background-image: url(@theme_image_path@/states/success_state.gif);
margin: auto;
max-width: 250px;
padding-top: 250px;
}
.taglib-empty-result-message .taglib-search-state {
background-image: url(@theme_image_path@/states/search_state.gif);
margin: auto;
max-width: 250px;
padding-top: 250px;
}
.taglib-empty-result-message .taglib-empty-result-message-title {
font-size: 1rem;
margin-top: 48px;
}
.taglib-empty-result-message .taglib-empty-result-message-description {
font-size: 0.875rem;
}
.taglib-empty-result-message .taglib-empty-result-message-header + .taglib-empty-result-message-title,
.taglib-empty-result-message .taglib-empty-search-result-message-header + .taglib-empty-result-message-title,
.taglib-empty-result-message .taglib-empty-state + .taglib-empty-result-message-title,
.taglib-empty-result-message .taglib-success-state + .taglib-empty-result-message-title,
.taglib-empty-result-message .taglib-search-state + .taglib-empty-result-message-title {
font-size: 1.25rem;
font-weight: bold;
text-align: center;
}
.taglib-empty-result-message .taglib-empty-result-message-header ~ .taglib-empty-result-message-description, .taglib-empty-result-message .taglib-empty-result-message-header ~ .taglib-empty-result-message-actions,
.taglib-empty-result-message .taglib-empty-search-result-message-header ~ .taglib-empty-result-message-description,
.taglib-empty-result-message .taglib-empty-search-result-message-header ~ .taglib-empty-result-message-actions,
.taglib-empty-result-message .taglib-empty-state ~ .taglib-empty-result-message-description,
.taglib-empty-result-message .taglib-empty-state ~ .taglib-empty-result-message-actions,
.taglib-empty-result-message .taglib-success-state ~ .taglib-empty-result-message-description,
.taglib-empty-result-message .taglib-success-state ~ .taglib-empty-result-message-actions,
.taglib-empty-result-message .taglib-search-state ~ .taglib-empty-result-message-description,
.taglib-empty-result-message .taglib-search-state ~ .taglib-empty-result-message-actions {
font-size: 1rem;
text-align: center;
}

.field-wrapper.form-inline .control-label {
display: inline-block;
}

.field-wrapper-html .input-localized .input-group-item {
flex-wrap: nowrap;
}

.taglib-header {
border-bottom: 1px solid #c8c9ca;
color: #555;
margin-bottom: 1em;
padding-bottom: 10px;
}
.taglib-header .header-title {
display: inline-block;
margin: 0.1em;
max-width: 100%;
text-overflow: ellipsis;
vertical-align: middle;
white-space: nowrap;
word-wrap: normal;
}
.taglib-header .header-back-to {
display: inline-block;
vertical-align: middle;
}
.taglib-header .header-back-to a {
display: block;
font-weight: bold;
padding: 0 0.3em 0 0;
text-decoration: none;
}

.taglib-icon {
text-decoration: none;
}
.taglib-icon:not(.btn):hover .taglib-text, .taglib-icon:not(.btn):focus .taglib-text {
text-decoration: underline;
}
.taglib-icon[lang] > img {
vertical-align: baseline;
width: 16px;
}

.icon-monospaced {
color: inherit;
display: inline-block;
height: 2rem;
line-height: 34px;
text-align: center;
width: 2rem;
}
.icon-monospaced.lexicon-icon {
padding: 8px;
}

.taglib-icon-list li {
float: left;
margin-right: 1em;
}
.taglib-icon-list:after {
clear: both;
content: "";
display: block;
height: 0;
visibility: hidden;
}

.lfr-icon-menu .lfr-icon-menu-icon {
max-width: none;
}
@media (max-width: 767.98px) {
.table-cell.last .lfr-icon-menu .dropdown-toggle {
	text-align: center;
}
.table-cell.last .lfr-icon-menu .dropdown-toggle > img,
.table-cell.last .lfr-icon-menu .dropdown-toggle .lfr-icon-menu-text {
	display: none;
}
}

.lfr-menu-list {
float: none;
overflow-y: auto;
position: relative;
}
.lfr-menu-list.direction-right {
margin: 0 2px;
}
.lfr-menu-list.dropdown-menu {
float: none;
position: relative;
}
@media (max-width: 991.98px) {
.lfr-menu-list.dropdown-menu > li > a {
	font-size: 22px;
	line-height: 40px;
	white-space: normal;
}
.lfr-menu-list.dropdown-menu > li > a img {
	margin-bottom: 3px;
}
}
.lfr-menu-list.dropdown-menu .search-panel {
margin-bottom: 0;
}
.lfr-menu-list.dropdown-menu .search-panel .form-group {
margin: 0 16px;
}
.lfr-menu-list.dropdown-menu .search-panel .menu-item-filter {
width: auto;
}
body > .lfr-menu-list ul {
border-radius: 4px;
}

.lfr-icon-menu-open:after {
clear: both;
content: "";
display: table;
}
.lfr-icon-menu-open .dropdown-menu {
position: static;
}

.input-localized.input-localized-editor .form-control {
border-width: 0;
height: 100%;
padding: 0;
}
.input-localized .input-group-item {
flex-direction: column;
}
.input-localized .input-localized-content {
margin-left: 6px;
}
.input-group .input-localized .input-localized-content {
margin-top: 3px;
}
.input-localized .lfr-input-localized .lfr-input-localized-state {
background-color: #ddd;
}
.input-localized .lfr-input-localized .lfr-input-localized-state.lfr-input-localized-state-error {
background-color: #ff0047;
}
.input-localized .lfr-input-localized-default a {
margin-right: 14px;
}
.input-localized .lfr-input-localized-state {
height: 4px;
margin: 3px 5px;
width: 4px;
}
.input-localized .palette-item-inner .lfr-input-localized-flag {
font-size: 16px;
opacity: 0.5;
}
.input-localized .palette-item,
.input-localized .palette-item-hover,
.input-localized .palette-item-hover .palette-item-inner {
border-color: transparent;
}
.input-localized .palette-item-inner {
border-color: #666;
display: block;
height: 16px;
width: 16px;
}
.input-localized .palette-item-selected .lfr-input-localized-state {
background: #27c1f2;
}

.input-localized-palette-container .palette-items-container {
font-size: 0;
margin: 0;
white-space: normal;
}
.input-localized-palette-container .palette-item {
border-width: 0;
padding: 0.5rem 1.25rem;
}
.input-localized-palette-container .palette-item-inner {
border: 1px solid;
margin-right: 4px;
width: 16px;
}
.input-localized-palette-container .palette-item-inner img {
display: block;
}

.taglib-move-boxes {
margin-bottom: 1em;
}
.taglib-move-boxes label {
border-bottom: 1px solid transparent;
display: block;
margin-bottom: 5px;
padding: 5px;
}
.taglib-move-boxes .toolbar {
text-align: center;
}
.taglib-move-boxes .arrow-button img {
border-width: 0;
height: 16px;
width: 16px;
}
.taglib-move-boxes .choice-selector {
width: 100%;
}
.taglib-move-boxes .choice-selector label {
background: #ebf1f9;
border-bottom-color: #8db2f3;
}
.taglib-move-boxes .field-content {
margin-bottom: 0;
}
.taglib-move-boxes .move-arrow-buttons {
margin-top: 5em;
}
.taglib-move-boxes .move-arrow-buttons .arrow-button {
display: block;
}
.taglib-move-boxes .sortable-container {
margin-top: 10px;
}
.taglib-move-boxes .sortable-container .btn.edit-selection {
margin-bottom: 10px;
}
.taglib-move-boxes .sortable-container .move-option {
background-color: transparent;
border-top: 1px solid #ddd;
display: none;
margin: 2px 0;
padding: 2px;
position: relative;
}
.taglib-move-boxes .sortable-container .move-option.selected {
display: block;
}
.taglib-move-boxes .sortable-container .move-option.move-option-dragging {
background-color: #fafafa;
}
.taglib-move-boxes .sortable-container .move-option .checkbox,
.taglib-move-boxes .sortable-container .move-option .handle {
position: absolute;
}
.taglib-move-boxes .sortable-container .move-option .checkbox {
display: none;
margin: 7px 0 0 5px;
}
.taglib-move-boxes .sortable-container .move-option .handle {
color: #999;
cursor: pointer;
font-size: 1.5em;
padding: 6px;
}
.taglib-move-boxes .sortable-container .move-option .title {
font-size: 1.2em;
margin: 1px 0 1px 30px;
}
.taglib-move-boxes .sortable-container.edit-list-active .move-option {
display: block;
}
.taglib-move-boxes .sortable-container.edit-list-active .move-option .checkbox {
display: inline-block;
}
.taglib-move-boxes .sortable-container.edit-list-active .move-option .handle {
display: none;
}
.mobile .taglib-move-boxes .selector-container {
display: none;
}

.item-selector .card-row > .autofit-col-expand {
padding-top: 4px;
}
.item-selector .drop-zone {
background-color: #f1f2f5;
margin-top: 1rem;
}
.item-selector .input-file {
height: 0.1px;
opacity: 0;
overflow: hidden;
position: absolute;
width: 0.1px;
z-index: -1;
}
.item-selector .input-file + label {
position: relative;
}
.item-selector .input-file + label::before {
border-radius: 4px;
bottom: -2px;
content: "";
display: block;
left: -2px;
position: absolute;
right: -2px;
top: -2px;
transition: box-shadow 0.15s ease-in-out;
}
.item-selector .input-file:focus + label::before {
box-shadow: 0 0 0 2px #fff, 0 0 0 4px #75b8ff;
}
.item-selector .item-selector-list-row:hover {
background-color: #e5f2ff;
cursor: pointer;
}

.lfr-map {
border: 1px solid #ccc;
min-height: 400px;
width: 100%;
}
.lfr-map .home-button {
margin: 5px;
}
@media (max-width: 575.98px) {
.lfr-map .home-button {
	height: 35px;
	width: 45px;
}
}
.lfr-map .search-controls {
font-size: 15px;
margin-top: 5px;
width: 100%;
}
@media (min-width: 576px) {
.lfr-map .search-controls {
	width: 50%;
}
}
.lfr-map .search-controls .search-input {
width: 100%;
}

.navbar .navbar-collapse {
max-height: none;
}
.navbar .navbar-collapse:after, .navbar .navbar-collapse:before {
display: inline;
}
@media (max-width: 767.98px) {
.navbar .navbar-search {
	background-color: #f3f3f3;
	border-top-width: 0;
	width: 100%;
}
}
.navbar .navbar-search .form-search {
margin: 8px 0;
}
@media (min-width: 768px) {
.navbar .navbar-search .form-search {
	float: right;
	max-width: 275px;
}
}
@media (max-width: 767.98px) {
.navbar .navbar-search .form-search .input-group {
	width: 100%;
}
}
@media (max-width: 991.98px) {
.navbar .container .navbar-btn,
.navbar .container-fluid .navbar-btn {
	margin-bottom: 5px;
	width: 45px;
}
.navbar .container .navbar-collapse.open,
.navbar .container-fluid .navbar-collapse.open {
	border-width: 0;
	display: block;
	height: auto;
	overflow: visible;
}
}

.taglib-portlet-preview.show-borders {
border: 1px solid #828f95;
margin-bottom: 1em;
padding: 3px 3px 1em;
}
.taglib-portlet-preview.show-borders .title {
background-color: #d3dadd;
font-size: 1.4em;
font-weight: bold;
padding: 0.5em;
}
.taglib-portlet-preview .preview {
margin: 1em;
min-height: 90px;
}

.lfr-search-container .entry-action {
width: 1px;
}
.lfr-search-container .entry-action .taglib-icon {
white-space: nowrap;
}

.lfr-search-container-wrapper.lfr-search-container-fixed-first-column {
position: relative;
}
@media (min-width: 576px) {
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive {
	margin-left: 375px;
	width: auto;
}
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils {
position: static;
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header {
left: 12px;
position: fixed;
right: 12px;
top: -1px;
z-index: 1020;
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header > th, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header > th {
display: block;
padding: 0;
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper {
overflow-x: hidden;
}
@media (min-width: 576px) {
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper {
	margin-left: 375px;
}
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper table, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper table {
border-collapse: collapse;
width: 100%;
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper table th, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils .lfr-search-iterator-fixed-header > th .lfr-search-iterator-fixed-header-inner-wrapper table th {
border-radius: 0;
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table td, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils td,
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table th,
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils th {
width: auto;
}
@media (min-width: 576px) {
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table td:first-child, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils td:first-child,
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table th:first-child,
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils th:first-child {
	left: 0;
	position: absolute;
	right: 15px;
}
}
.lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive .table th, .lfr-search-container-wrapper.lfr-search-container-fixed-first-column .table-responsive table.docutils th {
height: auto;
}
.lfr-search-container-wrapper a:not(.component-action):not(.btn) {
color: #343a40;
}
.lfr-search-container-wrapper .dropdown-action .dropdown-toggle {
color: #6c757d;
}
.lfr-search-container-wrapper .dropdown-action .dropdown-toggle:hover {
color: #343a40;
}
.lfr-search-container-wrapper .lfr-icon-menu > .dropdown-toggle {
color: #6c757d;
}
.lfr-search-container-wrapper .lfr-icon-menu > .dropdown-toggle:active, .lfr-search-container-wrapper .lfr-icon-menu > .dropdown-toggle:focus, .lfr-search-container-wrapper .lfr-icon-menu > .dropdown-toggle:hover {
background-color: #f7f8f9;
border-radius: 4px;
color: #343a40;
}
.lfr-search-container-wrapper .lfr-search-container-list .list-group:last-child .list-group-item:nth-last-child(2) {
border-bottom-left-radius: 0.25rem;
border-bottom-right-radius: 0.25rem;
}
.lfr-search-container-wrapper .lfr-search-container-list + .taglib-search-iterator-page-iterator-bottom {
margin-top: 20px;
}
.lfr-search-container-wrapper .list-group {
margin-bottom: 0;
}
.lfr-search-container-wrapper .list-group + .list-group .list-group-header:first-child,
.lfr-search-container-wrapper .list-group + .list-group .list-group-item:first-child {
border-top-left-radius: 0;
border-top-right-radius: 0;
}
.lfr-search-container-wrapper .list-group .list-group-item h4 {
font-size: 0.875rem;
line-height: 1.5;
margin-bottom: 0;
}
.lfr-search-container-wrapper .list-group .list-group-item h5,
.lfr-search-container-wrapper .list-group .list-group-item h6,
.lfr-search-container-wrapper .list-group .list-group-item .h5,
.lfr-search-container-wrapper .list-group .list-group-item .h6 {
font-size: 0.875rem;
font-weight: 400;
line-height: 1.5;
margin-bottom: 0;
}
.lfr-search-container-wrapper .list-group .list-group-item span + h2.h5 {
font-weight: 600;
}
.lfr-search-container-wrapper .list-group .list-group-item h6 + h5 {
font-weight: 600;
}
.lfr-search-container-wrapper .table-list tbody tr:nth-last-child(2) td:first-child,
.lfr-search-container-wrapper .table-list tbody tr:nth-last-child(2) th:first-child {
border-bottom-left-radius: 0.25rem;
}
.lfr-search-container-wrapper .table-list tbody tr:nth-last-child(2) td:last-child,
.lfr-search-container-wrapper .table-list tbody tr:nth-last-child(2) th:last-child {
border-bottom-right-radius: 0.25rem;
}

.user-info {
display: flex;
}
.user-info .sticker {
align-self: center;
}
.user-info .user-details {
margin-left: 1rem;
}
.user-info .user-name {
color: #343a40;
font-weight: 600;
}
.user-info .date-info {
color: #6c757d;
}

.user-status-tooltip .user-status-avatar {
float: left;
margin-right: 5px;
}
.user-status-tooltip .user-status-avatar .user-status-avatar-image {
width: 27px;
}
.user-status-tooltip .user-status-info {
display: inline-block;
overflow: hidden;
}
.user-status-tooltip .user-status-info .user-status-date {
font-size: 11px;
}

.lfr-search-container {
margin-top: 1.5em;
overflow: auto;
}
@media (max-width: 767.98px) {
.lfr-search-container .selector-button {
	width: auto;
}
}
.touch .lfr-search-container {
-webkit-overflow-scrolling: touch;
}
.touch .lfr-search-container .searchcontainer-content .table, .touch .lfr-search-container .searchcontainer-content table.docutils {
max-width: none;
}

.taglib-page-iterator {
clear: both;
height: auto;
width: auto;
}
.taglib-page-iterator .lfr-pagination-buttons {
float: right;
margin: 0;
}
@media (max-width: 991.98px) {
.taglib-page-iterator .lfr-pagination-buttons {
	width: 100%;
}
}
@media (max-width: 991.98px) and (max-width: 767.98px) {
.taglib-page-iterator .lfr-pagination-buttons {
	float: none;
	margin-top: 20px;
}
}
@media (max-width: 991.98px) {
.taglib-page-iterator .lfr-pagination-buttons > li {
	display: inline-block;
	width: 50%;
}
.taglib-page-iterator .lfr-pagination-buttons > li.first, .taglib-page-iterator .lfr-pagination-buttons > li.last {
	display: none;
}
.taglib-page-iterator .lfr-pagination-buttons > li > a {
	line-height: 20px;
	padding: 11px 19px;
}
}
.taglib-page-iterator .lfr-pagination-config {
float: left;
line-height: 46px;
}
@media (max-width: 767.98px) {
.taglib-page-iterator .lfr-pagination-config {
	float: none;
}
.taglib-page-iterator .lfr-pagination-config .current-page-menu {
	display: block;
}
.taglib-page-iterator .lfr-pagination-config .current-page-menu .btn {
	display: block;
}
}
.taglib-page-iterator .lfr-pagination-config .lfr-pagination-delta-selector,
.taglib-page-iterator .lfr-pagination-config .lfr-pagination-page-selector {
display: inline;
}
.taglib-page-iterator .lfr-pagination-config .lfr-pagination-delta-selector {
float: none;
}
@media (max-width: 991.98px) {
.taglib-page-iterator .lfr-pagination-config .lfr-pagination-delta-selector {
	display: none;
}
}
.taglib-page-iterator .search-results {
float: left;
line-height: 46px;
margin-left: 10px;
}
@media (max-width: 991.98px) {
.taglib-page-iterator .search-results {
	display: none;
}
}

.taglib-search-toggle .form-search {
position: relative;
}
.taglib-search-toggle .form-search .input-group-btn {
position: absolute;
}
@media (min-width: 992px) {
.taglib-search-toggle .form-search .input-group-btn {
	position: static;
}
}
.taglib-search-toggle .toggle-advanced {
color: inherit;
margin-left: 10px;
position: absolute;
top: 8px;
}
.taglib-search-toggle .toggle-advanced, .taglib-search-toggle .toggle-advanced:hover, .taglib-search-toggle .toggle-advanced:focus {
text-decoration: none;
}

.taglib-search-toggle-advanced-wrapper .taglib-search-toggle-advanced {
background-color: #fcfcfc;
border: solid #ddd;
border-width: 0 1px 1px;
margin-top: 0;
padding: 15px 15px 0;
}
.navbar-search .taglib-search-toggle-advanced-wrapper .taglib-search-toggle-advanced {
border-width: 1px 0 0;
}
.taglib-search-toggle-advanced-wrapper .taglib-search-toggle-advanced .taglib-search-toggle-advanced-content {
position: relative;
}
.taglib-search-toggle-advanced-wrapper .taglib-search-toggle-advanced .taglib-search-toggle-advanced-content .match-fields {
margin-bottom: 0;
}
.taglib-search-toggle-advanced-wrapper .taglib-search-toggle-advanced .taglib-search-toggle-advanced-content .match-fields-legend {
color: #999;
font-size: 13px;
}
.taglib-search-toggle-advanced-wrapper .btn.close {
margin-right: 5px;
position: relative;
z-index: 1;
}

.taglib-user-display {
padding: 0.5em;
}
.taglib-user-display .avatar {
background: no-repeat center;
background-size: 60px;
display: block;
height: 60px;
margin: 0 auto;
width: 60px;
}
.taglib-user-display .avatar.author:after {
background: rgba(50, 168, 230, 0.5);
border-radius: 50%;
content: "";
display: block;
height: 100%;
width: 100%;
}
.taglib-user-display .user-details {
margin-top: 1em;
}
.taglib-user-display .user-name {
font-size: 1.1em;
font-weight: bold;
}
.taglib-user-display a .user-name {
text-decoration: underline;
}
.taglib-user-display.display-style-1 .user-profile-image {
float: left;
margin-right: 24px;
}
.taglib-user-display.display-style-1 .user-name {
display: inline-block;
margin-top: 10px;
}
.taglib-user-display.display-style-1 .user-details {
margin-top: 0;
}
.taglib-user-display.display-style-2 .user-profile-image,
.taglib-user-display.display-style-2 .user-name {
clear: both;
display: block;
min-height: 20px;
text-align: center;
}
.taglib-user-display.display-style-3 {
padding: 0;
}
.taglib-user-display.display-style-3 .user-profile-image {
display: inline-block;
vertical-align: middle;
}

.taglib-workflow-status .workflow-id,
.taglib-workflow-status .workflow-version,
.taglib-workflow-status .workflow-status {
color: #999;
}
.table-cell .taglib-workflow-status {
margin: 0;
}
.table-cell .taglib-workflow-status .workflow-status {
padding-left: 0;
}

.lfr-autocomplete-input-list .yui3-aclist-list {
margin: 0;
}

.portal-popup .sheet > .lfr-nav {
margin-top: -24px;
}
.portal-popup .contacts-portlet .portlet-configuration-container .form {
position: static;
}
.portal-popup .lfr-form-content {
padding: 24px 12px;
}
.portal-popup .portlet-body,
.portal-popup .portlet-boundary,
.portal-popup .portlet-column,
.portal-popup .portlet-layout {
height: 100%;
}
.portal-popup .portlet-column {
position: static;
}
.portal-popup .dialog-body > .container-fluid-max-xl,
.portal-popup .dialog-body .container-view,
.portal-popup .export-dialog-tree > .container-fluid-max-xl,
.portal-popup .export-dialog-tree .container-view,
.portal-popup .lfr-dynamic-uploader > .container-fluid-max-xl,
.portal-popup .lfr-dynamic-uploader .container-view,
.portal-popup .lfr-form-content > .container-fluid-max-xl,
.portal-popup .lfr-form-content .container-view,
.portal-popup .portlet-configuration-body-content > .container-fluid-max-xl,
.portal-popup .portlet-configuration-body-content .container-view,
.portal-popup .process-list > .container-fluid-max-xl,
.portal-popup .process-list .container-view,
.portal-popup .roles-selector-body > .container-fluid-max-xl,
.portal-popup .roles-selector-body .container-view {
padding-top: 20px;
}
.portal-popup .dialog-body > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .dialog-body .container-view .nav-tabs-underline,
.portal-popup .export-dialog-tree > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .export-dialog-tree .container-view .nav-tabs-underline,
.portal-popup .lfr-dynamic-uploader > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .lfr-dynamic-uploader .container-view .nav-tabs-underline,
.portal-popup .lfr-form-content > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .lfr-form-content .container-view .nav-tabs-underline,
.portal-popup .portlet-configuration-body-content > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .portlet-configuration-body-content .container-view .nav-tabs-underline,
.portal-popup .process-list > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .process-list .container-view .nav-tabs-underline,
.portal-popup .roles-selector-body > .container-fluid-max-xl .nav-tabs-underline,
.portal-popup .roles-selector-body .container-view .nav-tabs-underline {
margin-left: -15px;
margin-right: -15px;
margin-top: -20px;
}
.portal-popup .dialog-body > .lfr-nav + .container-fluid-max-xl,
.portal-popup .export-dialog-tree > .lfr-nav + .container-fluid-max-xl,
.portal-popup .lfr-dynamic-uploader > .lfr-nav + .container-fluid-max-xl,
.portal-popup .lfr-form-content > .lfr-nav + .container-fluid-max-xl,
.portal-popup .portlet-configuration-body-content > .lfr-nav + .container-fluid-max-xl,
.portal-popup .process-list > .lfr-nav + .container-fluid-max-xl,
.portal-popup .roles-selector-body > .lfr-nav + .container-fluid-max-xl {
padding-top: 0;
}
.portal-popup .login-container {
padding: 1rem;
}
.portal-popup .management-bar-default {
border-left-width: 0;
border-radius: 0;
border-right-width: 0;
border-top-width: 0;
margin-bottom: 0;
}
.portal-popup .navbar ~ .portlet-configuration-setup,
.portal-popup .portlet-export-import-container {
height: calc(100% - 48px);
position: relative;
}
@media (min-width: 576px) {
.portal-popup .navbar ~ .portlet-configuration-setup,
.portal-popup .portlet-export-import-container {
	height: calc(100% - 48px);
}
}
.portal-popup .panel-group .panel {
border-left-width: 0;
border-radius: 0;
border-right-width: 0;
}
.portal-popup .panel-group .panel + .panel {
border-top-width: 0;
margin-top: 0;
}
.portal-popup .panel-heading {
border-top-left-radius: 0;
border-top-right-radius: 0;
}
.portal-popup .portlet-configuration-setup .lfr-nav {
margin-left: auto;
margin-right: auto;
max-width: 1280px;
padding-left: 3px;
padding-right: 3px;
}
@media (min-width: 576px) {
.portal-popup .portlet-configuration-setup .lfr-nav {
	padding-left: 8px;
	padding-right: 8px;
}
}
.portal-popup .lfr-dynamic-uploader,
.portal-popup .process-list {
bottom: 0;
display: block;
left: 0;
overflow: auto;
position: absolute;
right: 0;
top: 48px;
-webkit-overflow-scrolling: touch;
}
@media (min-width: 576px) {
.portal-popup .lfr-dynamic-uploader,
.portal-popup .process-list {
	top: 48px;
}
}
.portal-popup .portlet-export-import-publish-processes {
top: 0;
}
.portal-popup .dialog-footer {
background-color: #fff;
border-top: 1px solid #dee2e6;
bottom: 0;
display: flex;
flex-direction: row-reverse;
left: 0;
margin: 0;
padding: 10px 24px;
width: 100%;
z-index: 1020;
}
@media (min-width: 768px) {
.portal-popup .dialog-footer {
	position: fixed;
}
}
.portal-popup .dialog-footer .btn {
margin-left: 1rem;
margin-right: 0;
}
@media (min-width: 768px) {
.portal-popup .dialog-body:not(:last-child),
.portal-popup .lfr-dynamic-uploader:not(:last-child),
.portal-popup .lfr-form-content:not(:last-child),
.portal-popup .portlet-configuration-body-content:not(:last-child),
.portal-popup .roles-selector-body:not(:last-child) {
	padding-bottom: 60px;
}
}
.portal-popup .lfr-dynamic-uploader {
display: table;
table-layout: fixed;
width: 100%;
}
.portal-popup .lfr-dynamic-uploader.hide-dialog-footer {
bottom: 0;
}
.portal-popup .lfr-dynamic-uploader.hide-dialog-footer + .dialog-footer {
display: none;
}
.portal-popup .portlet-configuration-edit-permissions .portlet-configuration-body-content {
display: flex;
flex-direction: column;
overflow: visible;
}
.portal-popup .portlet-configuration-edit-permissions .portlet-configuration-body-content > form {
flex-grow: 1;
max-width: none;
overflow: auto;
}
.portal-popup .portlet-configuration-edit-templates .portlet-configuration-body-content {
bottom: 0;
}
.portal-popup:not(.article-preview) #main-content,
.portal-popup:not(.article-preview) #wrapper {
bottom: 0;
left: 0;
overflow: auto;
padding: 0;
position: absolute;
right: 0;
top: 0;
-webkit-overflow-scrolling: touch;
}
@media print {
.portal-popup:not(.article-preview) #main-content,
.portal-popup:not(.article-preview) #wrapper {
	position: initial;
}
}
.portal-popup .columns-max > .portlet-layout.row {
margin-left: 0;
margin-right: 0;
}
.portal-popup .columns-max > .portlet-layout.row > .portlet-column {
padding-left: 0;
padding-right: 0;
}

html:not(#__):not(#___) .portlet-layout.dragging {
border-collapse: separate;
}
html:not(#__):not(#___) .drop-area {
background-color: #d3dadd;
}
html:not(#__):not(#___) .active-area {
background: #ffc;
}
html:not(#__):not(#___) .portlet-boundary.yui3-dd-dragging {
opacity: 0.6;
}
html:not(#__):not(#___) .portlet-boundary.yui3-dd-dragging .portlet {
border: 2px dashed #ccc;
}
html:not(#__):not(#___) .sortable-layout-proxy {
opacity: 1;
}
html:not(#__):not(#___) .sortable-layout-proxy .portlet-topper {
background-image: none;
}
html:not(#__):not(#___) .proxy {
cursor: move;
opacity: 0.65;
position: absolute;
}
html:not(#__):not(#___) .proxy.generic-portlet {
height: 200px;
width: 300px;
}
html:not(#__):not(#___) .proxy.generic-portlet .portlet-title {
padding: 10px;
}
html:not(#__):not(#___) .proxy.not-intersecting .forbidden-action {
background: url(../images/application/forbidden_action.png) no-repeat;
display: block;
height: 32px;
position: absolute;
right: -15px;
top: -15px;
width: 32px;
}
html:not(#__):not(#___) .resizable-proxy {
border: 1px dashed #828f95;
position: absolute;
visibility: hidden;
}
html:not(#__):not(#___) .sortable-proxy {
background: #727c81;
margin-top: 1px;
}
html:not(#__):not(#___) .sortable-layout-drag-target-indicator {
margin: 2px 0;
}
html:not(#__):not(#___) .yui3-dd-proxy {
z-index: 1110 !important;
}

.portlet-layout.dragging {
border-collapse: separate;
}

.drop-area {
background-color: #d3dadd;
}

.active-area {
background: #ffc;
}

.portlet-boundary.yui3-dd-dragging {
opacity: 0.6;
transform: scale(0.8);
transition: transform 0.3s ease;
}
@media (prefers-reduced-motion: reduce) {
.portlet-boundary.yui3-dd-dragging {
	transition: none;
}
}
.portlet-boundary.yui3-dd-dragging .portlet {
border: 2px dashed #ccc;
}

.sortable-layout-proxy {
opacity: 1;
}
.sortable-layout-proxy .portlet-topper {
background-image: none;
}

.proxy {
cursor: move;
opacity: 0.65;
position: absolute;
}
.proxy.generic-portlet {
height: 200px;
width: 300px;
}
.proxy.generic-portlet .portlet-title {
padding: 10px;
}
.proxy.not-intersecting .forbidden-action {
background: url(../images/application/forbidden_action.png) no-repeat;
display: block;
height: 32px;
position: absolute;
right: -15px;
top: -15px;
width: 32px;
}

.resizable-proxy {
border: 1px dashed #828f95;
position: absolute;
visibility: hidden;
}

.sortable-proxy {
background: #727c81;
margin-top: 1px;
}

.sortable-layout-drag-target-indicator {
margin: 2px 0;
}

.yui3-dd-proxy {
z-index: 1110 !important;
}

.portlet-column-content.empty {
padding: 50px;
}

.lfr-portlet-title-editable {
margin-top: 0;
z-index: 9999;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content {
padding: 0;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content .field-input {
margin-bottom: 0;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content .textfield-label {
display: none;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content .btn-toolbar-content {
display: inline-block;
vertical-align: bottom;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content .btn-group {
margin-top: 0;
}
.lfr-portlet-title-editable .lfr-portlet-title-editable-content .btn {
display: inline-block;
float: none;
margin-top: 0;
width: auto;
}

.lfr-source-editor {
border: solid 0 #ccc;
border-bottom-width: 2px;
position: relative;
}
.lfr-source-editor .ace_editor {
height: 100%;
}
.lfr-source-editor .lfr-source-editor-toolbar li > .btn {
background-color: #fff;
border: transparent;
color: #717383;
outline: 0;
}
.lfr-source-editor .lfr-source-editor-code {
background-color: #fff;
color: #2b4259;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter {
background-color: #ededef;
color: #868896;
overflow: hidden;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_fold-widget {
font-family: fontawesome-alloy;
text-align: center;
vertical-align: middle;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_fold-widget.ace_open, .lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_fold-widget.ace_closed {
background-image: none;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_fold-widget.ace_open:before {
content: "▾";
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_fold-widget.ace_closed:before {
content: "▸";
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_gutter-active-cell {
color: #fff;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_gutter-active-line {
background-color: #717383;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_gutter-layer {
border-right: solid 1px #ccc;
}
.lfr-source-editor .lfr-source-editor-code .ace_gutter .ace_info {
background-image: none;
}
.lfr-source-editor .lfr-source-editor-code .ace_content .ace_active-line {
background-color: #ededef;
}
.lfr-source-editor .lfr-source-editor-code .ace_content .ace_constant {
color: #34adab;
}
.lfr-source-editor .lfr-source-editor-code .ace_content .ace_tag {
color: #1d5ec7;
}
.lfr-source-editor .lfr-source-editor-code .ace_content .ace_string {
color: #ff6c58;
}
.lfr-source-editor .lfr-source-editor-code .ace_content .ace_string.ace_regex {
color: #f00;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code {
background-color: #47474f;
color: #fff;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code .ace_gutter {
background: #54555e;
color: #fff;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code .ace_gutter .ace_gutter-active-line {
background-color: #009aed;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code .ace_content .ace_active-line {
background-color: #11394e;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code .ace_content .ace_cursor {
color: #fff;
}
.lfr-source-editor.ace_dark .lfr-source-editor-code .ace_content .ace_tag {
color: #4d91ff;
}

.lfr-fullscreen-source-editor {
height: 100%;
overflow: hidden;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-header {
height: 40px;
margin-right: 4px;
margin-top: 4px;
min-height: 40px;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content {
height: 95%;
position: relative;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content .panel-splitter {
border: 1px solid #ccc;
position: absolute;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content .preview-panel {
display: inline-block;
overflow-y: auto;
padding-left: 20px;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content .source-panel {
display: inline-block;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.vertical .source-panel,
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.vertical .preview-panel {
height: 100%;
width: 50%;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.vertical .panel-splitter {
height: 100%;
left: 50%;
top: 0;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.horizontal .source-panel,
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.horizontal .preview-panel {
height: 50%;
width: 100%;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.horizontal .panel-splitter {
top: 50%;
width: 100%;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.simple .panel-splitter,
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.simple .preview-panel {
display: none;
}
.lfr-fullscreen-source-editor .lfr-fullscreen-source-editor-content.simple .source-panel {
height: 100%;
width: 100%;
}

.lfr-fulscreen-source-editor-dialog .modal-footer {
text-align: left;
}

.file-icon-color-0 {
background-color: #fff;
color: #6b6c7e;
}

.file-icon-color-1 {
background-color: #fff;
color: #a7a9bc;
}

.file-icon-color-2 {
background-color: #fff;
color: #50d2a0;
}

.file-icon-color-3 {
background-color: #fff;
color: #af78ff;
}

.file-icon-color-4 {
background-color: #fff;
color: #ffb46e;
}

.file-icon-color-5 {
background-color: #fff;
color: #ff5f5f;
}

.file-icon-color-6 {
background-color: #fff;
color: #4b9bff;
}

.file-icon-color-7 {
background-color: #fff;
color: #272833;
}

.lfr-item-viewer.uploading > div:not(.progress-container) {
opacity: 0.3;
}
.lfr-item-viewer.uploading > .progress-container {
visibility: visible;
}
.lfr-item-viewer .aspect-ratio a.item-preview {
background-position: center center;
background-repeat: no-repeat;
background-size: cover;
height: 100%;
position: absolute;
width: 100%;
}
.lfr-item-viewer .image-viewer-base-image-list {
padding-top: 35px;
}
.lfr-item-viewer .item-preview:hover {
cursor: pointer;
}
.lfr-item-viewer .progress-container {
background-color: #fff;
left: 0;
margin: 0 auto;
padding: 20px 30px;
position: absolute;
right: 0;
text-align: center;
top: 50%;
transform: translateY(-50%);
visibility: hidden;
width: 80%;
z-index: 1000;
}
.lfr-item-viewer .progress-container a {
position: absolute;
right: 30px;
}
.lfr-item-viewer .progress-container .progress {
margin-top: 10px;
}
.lfr-item-viewer .search-info {
background-color: #d3e8f1;
}
.lfr-item-viewer .search-info .keywords {
font-size: 1.4em;
font-weight: bold;
}
.lfr-item-viewer .search-info .change-search-folder {
font-size: 0.8em;
font-weight: normal;
}
.lfr-item-viewer .upload-view {
display: table;
height: 400px;
margin-top: 20px;
width: 100%;
}
.lfr-item-viewer .upload-view > div {
display: table-cell;
vertical-align: middle;
}
.lfr-item-viewer .yui3-widget-bd {
position: relative;
}

.lfr-menu-expanded li a:focus {
background-color: #5b677d;
color: #fff;
text-shadow: -1px -1px #2c2f34;
}

.lfr-url-error {
display: inline-block;
white-space: normal;
overflow-wrap: break-all;
word-wrap: break-all;
}

.lfr-page-layouts {
padding: 0;
}
.lfr-page-layouts input[type=radio] {
opacity: 0;
position: absolute;
}
.lfr-page-layouts input[type=radio]:checked + .card-horizontal {
cursor: default;
}
.lfr-page-layouts input[type=radio]:checked + .card-horizontal::after {
bottom: -0.0625rem;
content: "";
left: -0.0625rem;
position: absolute;
right: -0.0625rem;
transition: height 0.15s ease-out;
}
@media (prefers-reduced-motion: reduce) {
.lfr-page-layouts input[type=radio]:checked + .card-horizontal::after {
	transition: none;
}
}
.lfr-page-layouts .card-horizontal {
cursor: pointer;
outline: 0;
transition: color 0.15s ease-in-out, background-color 0.15s ease-in-out, border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}
@media (prefers-reduced-motion: reduce) {
.lfr-page-layouts .card-horizontal {
	transition: none;
}
}
.lfr-page-layouts .card-horizontal::after {
border-radius: 0 0 0.25rem 0.25rem;
bottom: -0.0625rem;
content: "";
height: 0;
left: -0.0625rem;
position: absolute;
right: -0.0625rem;
transition: height 0.15s ease-out;
}
@media (prefers-reduced-motion: reduce) {
.lfr-page-layouts .card-horizontal::after {
	transition: none;
}
}
.modal-body.dialog-iframe-bd {
overflow: hidden;
padding: 0;
}

.modal-dialog:not(.dialog-iframe-modal):not(.modal-full-screen) {
position: relative;
}
.modal-dialog.dialog-iframe-modal {
max-width: none;
}
.modal-dialog.modal-dialog-sm {
max-width: 500px;
}
.modal-dialog .yui3-resize-handles-wrapper {
pointer-events: all;
}
.modal-dialog .yui3-resize-handles-wrapper .yui3-resize-handle-inner-br {
bottom: 0;
right: 0;
}

.modal-open .modal {
display: block;
}

.sheet > .panel-group .sheet-footer {
margin-bottom: 0;
}

.sheet-footer .btn {
margin-right: 0.5rem;
}
.sheet-footer .btn:last-child {
margin-right: 0;
}

.tag-items {
list-style: none;
margin: 0 1em 0 0;
padding: 0 1em 0.5em;
white-space: normal;
}
.tag-items li {
display: inline-block;
margin: 0 1em 0 0;
max-width: 100%;
}

.tag-selected {
color: #000;
font-weight: bold;
text-decoration: none;
}

.tag-cloud .tag-popularity-1 {
font-size: 1em;
}
.tag-cloud .tag-popularity-2 {
font-size: 1.3em;
}
.tag-cloud .tag-popularity-3 {
font-size: 1.6em;
}
.tag-cloud .tag-popularity-4 {
font-size: 1.9em;
}
.tag-cloud .tag-popularity-5 {
font-size: 2.2em;
}
.tag-cloud .tag-popularity-6 {
font-size: 2.5em;
}

.lfr-portal-tooltip {
display: inline-flex;
}
.lfr-portal-tooltip,
.lfr-portal-tooltip a {
-webkit-touch-callout: none;
-moz-user-select: none;
-ms-user-select: none;
-webkit-user-select: none;
user-select: none;
}

.tree-node .icon-check {
padding-right: 2px;
}
.tree-node .tree-node-checked .icon-check {
padding-right: 0;
}
.tree-node [class^=icon-] {
margin-right: 5px;
}
.tree-node .tree-node-checkbox-container {
margin-right: 0;
}
.tree-node .tree-node-selected .tree-label {
background: none;
}

.tree-node-content .tree-hitarea {
color: #999;
font-size: 10px;
padding-right: 6px;
}
.tree-node-content .tree-label {
margin-left: 3px;
}
.tree-node-content .tree-node-hidden-hitarea {
visibility: hidden;
}

.tree-node-selected .tree-label {
background-color: transparent;
}

.tree-view li.tree-node .tree-node-content svg.lexicon-icon {
pointer-events: none;
}

.user-icon-color-0 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #6b6c7e;
}

.user-icon-color-1 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #4b9bff;
}

.user-icon-color-2 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #ffb46e;
}

.user-icon-color-3 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #ff5f5f;
}

.user-icon-color-4 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #50d2a0;
}

.user-icon-color-5 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #ff73c3;
}

.user-icon-color-6 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #9be169;
}

.user-icon-color-7 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #af78ff;
}

.user-icon-color-8 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #ffd76e;
}

.user-icon-color-9 {
background-color: #fff;
box-shadow: 0 0 0 1px #dee2e6;
color: #5fc8ff;
}

.collapse.open {
display: block;
}

.navbar-toggler-icon {
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline%20bars-line-top'%20d='M480%2064H32C14.336%2064%200%2049.664%200%2032S14.336%200%2032%200h448c17.664%200%2032%2014.336%2032%2032s-14.336%2032-32%2032z'%20fill='%23212529'/%3E%3Cpath%20class='lexicon-icon-outline%20bars-line-middle'%20d='M480%20288H32c-17.664%200-32-14.336-32-32s14.336-32%2032-32h448c17.664%200%2032%2014.336%2032%2032s-14.336%2032-32%2032z'%20fill='%23212529'/%3E%3Cpath%20class='lexicon-icon-outline%20bars-line-bottom'%20d='M480%20512H32c-17.664%200-32-14.336-32-32s14.336-32%2032-32h448c17.664%200%2032%2014.336%2032%2032s-14.336%2032-32%2032z'%20fill='%23212529'/%3E%3C/svg%3E");
height: 1em;
width: 1em;
}

.navbar-nav .nav-item.hover:after {
bottom: -0.125rem;
content: "";
height: 0.125rem;
left: 0;
position: absolute;
width: 100%;
}

.navbar .navbar-toggler .c-inner {
max-width: none;
}

html:not(#__):not(#___) .cadmin.portlet-topper {
position: relative;
}
html:not(#__):not(#___) .cadmin.portlet-topper .portlet-topper-toolbar .portlet-icon-back {
background: url(../images/arrows/12_left.png) no-repeat 0 50%;
padding: 5px 5px 5px 18px;
}
html:not(#__):not(#___) .cadmin.portlet-topper .portlet-topper-toolbar .portlet-options .lfr-icon-menu-text {
display: none;
}

body.portlet {
border-width: 0;
}

.portlet-icon-back {
margin-top: -2px;
}
.portlet-topper .portlet-topper-toolbar .portlet-icon-back {
background: url(../images/arrows/12_left.png) no-repeat 0 50%;
padding: 5px 5px 5px 18px;
}

.portlet-topper {
position: relative;
}
.portlet-topper .portlet-topper-toolbar .portlet-options .lfr-icon-menu-text {
display: none;
}
.portlet-draggable .portlet-topper {
cursor: move;
}

.portlet-title-editable {
cursor: pointer;
}

.portlet-title-text {
display: inline-block;
margin-top: 0;
max-width: 95%;
overflow: hidden;
text-overflow: ellipsis;
vertical-align: top;
white-space: nowrap;
}
.panel-page-body .portlet-title-text, .panel-page-content .portlet-title-text {
cursor: auto;
}

.portlet-minimized .portlet-content {
padding: 0;
}

.portlet-nested-portlets .portlet-boundary {
left: 0 !important;
position: relative !important;
top: 0 !important;
}

.portlet-layout .portlet-header {
margin-bottom: 1rem;
}

@media (min-width: 576px) {
.portlet .visible-interaction {
	display: none;
}
}
@media (max-width: 767.98px) {
.controls-hidden .portlet .visible-interaction {
	display: none;
}
}
.portlet:hover .visible-interaction, .portlet.open .visible-interaction {
display: block;
}

.controls-hidden .lfr-meta-actions,
.controls-hidden .lfr-configurator-visibility {
display: none;
}
.controls-hidden .portlet-topper-toolbar {
display: none !important;
}

html:not(#__):not(#___) .cadmin.portlet-topper {
background-color: #f7f7f7;
border-color: transparent;
border-radius: 0 0;
border-style: solid;
border-width: 1px 1px 1px 1px;
color: rgba(0, 0, 0, 0.5);
display: box;
display: flex;
padding: 3px 12px 3px 24px;
position: relative;
}
html:not(#__):not(#___) .portlet > .cadmin.portlet-topper {
display: none;
}
@media (min-width: 768px) {
html:not(#__):not(#___) .portlet > .cadmin.portlet-topper {
	display: flex;
	left: 0;
	opacity: 0;
	position: absolute;
	right: 0;
	transition: opacity 0.25s, transform 0.25s;
	top: 0;
}
}
@media (min-width: 768px) and (prefers-reduced-motion: reduce) {
html:not(#__):not(#___) .portlet > .cadmin.portlet-topper {
	transition: none;
}
}
html:not(#__):not(#___) .cadmin .portlet-actions {
float: right;
}
html:not(#__):not(#___) .cadmin .portlet-options {
display: inline-block;
}
html:not(#__):not(#___) .cadmin .portlet-title-menu {
flex: 0 1 auto;
}
html:not(#__):not(#___) .cadmin .portlet-title-menu > span > a {
display: inline-block;
text-decoration: none;
}
html:not(#__):not(#___) .cadmin .portlet-topper-toolbar {
margin: 0;
padding-left: 0;
}
html:not(#__):not(#___) .cadmin .portlet-topper-toolbar > a,
html:not(#__):not(#___) .cadmin .portlet-topper-toolbar > span > a,
html:not(#__):not(#___) .cadmin .portlet-topper-toolbar .lfr-icon-menu > a {
color: rgba(0, 0, 0, 0.5);
}
html:not(#__):not(#___) .cadmin .portlet-topper-toolbar > a:focus, html:not(#__):not(#___) .cadmin .portlet-topper-toolbar > a:hover {
text-decoration: none;
}
html:not(#__):not(#___) .cadmin .portlet-name-text {
font-size: 14px;
font-weight: 600;
}
html:not(#__):not(#___) .cadmin .portlet-title-default {
flex: 1 1 auto;
line-height: 2;
overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;
}
@media (min-width: 768px) {
html:not(#__):not(#___) .controls-visible .portlet:hover > .portlet-content-editable, html:not(#__):not(#___) .controls-visible .portlet.open > .portlet-content-editable, html:not(#__):not(#___) .controls-visible .portlet.focus > .portlet-content-editable {
	border-color: transparent;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
}
@media (min-width: 768px) {
html:not(#__):not(#___) .controls-visible .portlet:hover > .cadmin.portlet-topper, html:not(#__):not(#___) .controls-visible .portlet.open > .cadmin.portlet-topper, html:not(#__):not(#___) .controls-visible .portlet.focus > .cadmin.portlet-topper {
	opacity: 1;
	transform: translateY(-97%);
}
}
@media (max-width: 767.98px) {
html:not(#__):not(#___) .controls-visible .cadmin.portlet-topper {
	display: box;
	display: flex;
}
}
html:not(#__):not(#___) .controls-visible .cadmin .portlet-topper-toolbar {
display: block;
}

.portlet {
margin-bottom: 10px;
position: relative;
}
@media (min-width: 768px) {
.controls-visible .portlet:hover > .portlet-content-editable, .controls-visible .portlet.open > .portlet-content-editable, .controls-visible .portlet.focus > .portlet-content-editable {
	border-color: transparent;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
}
@media (min-width: 768px) {
.controls-visible .portlet:hover > .portlet-topper, .controls-visible .portlet.open > .portlet-topper, .controls-visible .portlet.focus > .portlet-topper {
	opacity: 1;
	transform: translateY(-97%);
}
}

.portlet-content-editable {
border-color: transparent;
border-radius: 0 0;
border-style: solid;
border-width: 1px 1px 1px 1px;
}
.portlet > .portlet-content-editable {
border-color: transparent;
}
@media (max-width: 767.98px) {
.controls-visible .portlet-content-editable {
	border-color: transparent;
	border-top-left-radius: 0;
	border-top-right-radius: 0;
}
}

.portlet-name-text {
font-size: 0.875rem;
font-weight: 600;
}

.portlet-options {
display: inline-block;
}

.portlet-title-default {
flex: 1 1 auto;
line-height: 2;
overflow: hidden;
text-overflow: ellipsis;
white-space: nowrap;
}

.portlet-title-menu {
flex: 0 1 auto;
}
.portlet-title-menu > span > a {
display: inline-block;
text-decoration: none;
}

.portlet-topper {
background-color: #f7f7f7;
border-color: transparent;
border-radius: 0 0;
border-style: solid;
border-width: 1px 1px 1px 1px;
color: rgba(0, 0, 0, 0.5);
display: box;
display: flex;
padding: 3px 12px 3px 24px;
position: relative;
}
.portlet > .portlet-topper {
display: none;
}
@media (min-width: 768px) {
.portlet > .portlet-topper {
	display: flex;
	left: 0;
	opacity: 0;
	position: absolute;
	right: 0;
	transition: opacity 0.25s, transform 0.25s;
	top: 0;
}
}
@media (min-width: 768px) and (prefers-reduced-motion: reduce) {
.portlet > .portlet-topper {
	transition: none;
}
}
@media (max-width: 767.98px) {
.controls-visible .portlet-topper {
	display: box;
	display: flex;
}
}

.portlet-topper-toolbar {
margin: 0;
padding-left: 0;
}
.portlet-topper-toolbar > a,
.portlet-topper-toolbar > span > a,
.portlet-topper-toolbar .lfr-icon-menu > a {
color: rgba(0, 0, 0, 0.5);
}
.portlet-topper-toolbar > a:focus, .portlet-topper-toolbar > a:hover {
text-decoration: none;
}
.controls-visible .portlet-topper-toolbar {
display: block;
}

.lfr-configurator-visibility .portlet-content .lfr-icon-actions {
opacity: 1;
}

.lfr-panel-page .portlet-title {
font-size: 13px;
}

.lfr-panel-page {
width: 100%;
}
.lfr-panel-page .lfr-add-content h2 span {
background-color: #d3dadd;
}

.portlet-borderless .portlet-content {
padding: 1rem;
}
.portlet-decorate .portlet-content {
background: #fff;
border-color: transparent;
border-style: solid;
border-width: 1px 1px 1px 1px;
padding: 1rem;
word-wrap: break-word;
}
.portlet-barebone .portlet-content {
padding: 0;
}

.portlet-dynamic-data-lists-display .lfr-ddm-field-group,
.portlet-dynamic-data-lists .lfr-ddm-field-group {
margin-bottom: 10px;
margin-top: 10px;
padding: 28px 24px;
}

.breadcrumb.breadcrumb-vertical {
display: inline-block;
text-align: center;
}
.breadcrumb.breadcrumb-vertical li {
display: block;
}
.breadcrumb.breadcrumb-vertical li.last, .breadcrumb.breadcrumb-vertical li.only {
background: none;
}
.breadcrumb.breadcrumb-vertical .divider {
background: url(../images/arrows/07_down.png) no-repeat 50% 100%;
display: block;
height: 10px;
overflow: hidden;
text-indent: 101%;
white-space: nowrap;
}

.navbar form {
margin: 0;
}

:not(.inline-item) > .loading-animation {
margin-bottom: 20px;
margin-top: 20px;
}
.product-menu .loading-animation {
margin-top: 160px;
}

@keyframes lfr-drop-active {
0% {
	background-color: #ebebeb;
	border-color: #ddd;
}
50% {
	background-color: #ddedde;
	border-color: #7d7;
	transform: scale(1.1);
}
75% {
	background-color: #ddedde;
	border-color: #7d7;
}
100% {
	background-color: #ebebeb;
	border-color: #ddd;
}
}
.lfr-upload-container .progress-bar,
.lfr-upload-container .progress {
border-radius: 10px;
}
.lfr-upload-container .upload-file,
.lfr-upload-container .upload-target {
border-radius: 5px;
}
.upload-drop-active .lfr-upload-container .upload-target {
animation: none;
}
.upload-drop-intent .lfr-upload-container .upload-target {
animation: lfr-drop-active 1s ease 0.2s infinite;
}

.select-files {
border-radius: 5px;
}

.taglib-form-navigator > .form-steps > ul.form-navigator.list-group {
box-shadow: none;
}
.taglib-form-navigator > .form-steps > ul.form-navigator.list-group .tab .tab-label:hover .number, .taglib-form-navigator > .form-steps > ul.form-navigator.list-group .tab .tab-label:focus .number {
box-shadow: 0 0 5px 0 #333;
transition-duration: 0.25s;
transition-property: box-shadow;
transition-timing-function: ease-out;
}
.taglib-form-navigator > .form-steps > ul.form-navigator.list-group .tab .tab-label .number {
border-radius: 50%;
}

@keyframes progress-bar-stripes {
from {
	background-position: 40px 0;
}
to {
	background-position: 0 0;
}
}
.lfr-progress-active .progress-bar-status,
.lfr-upload-container .file-uploading .progress-bar .progress {
animation: progress-bar-stripes 0.5s linear infinite;
background-image: linear-gradient(-45deg, rgba(255, 255, 255, 0.3) 25%, rgba(255, 255, 255, 0) 25%, rgba(255, 255, 255, 0) 50%, rgba(255, 255, 255, 0.3) 50%, rgba(255, 255, 255, 0.3) 75%, rgba(255, 255, 255, 0) 75%, rgba(255, 255, 255, 0));
background-size: 40px 40px;
transition: width, 0.5s, ease-out;
}
@media (prefers-reduced-motion: reduce) {
.lfr-progress-active .progress-bar-status,
.lfr-upload-container .file-uploading .progress-bar .progress {
	transition: none;
}
}

@keyframes highlight-animation {
from {
	background-color: #ffc;
}
to {
	background-color: transparent;
}
}
.highlight-animation {
animation: highlight-animation 0.7s;
}

.portlet-options.btn-group .dropdown-toggle, .portlet-options.btn-group.open .dropdown-toggle {
box-shadow: none;
}

.form-group.form-inline.input-boolean-wrapper label, .form-group.form-inline.input-checkbox-wrapper label {
gap: 0.3125rem;
}

table {
border-collapse: collapse;
}

th {
height: 20px;
text-align: left;
}

caption {
color: #6c757d;
padding-bottom: 0.75rem;
padding-top: 0.75rem;
text-align: left;
}

.table-head-title .inline-item-before {
margin-right: 0.25rem;
}
.table-head-title .inline-item-before + .text-truncate-inline {
max-width: calc(
	100% - 1em - 0.25rem
);
}
.table-head-title .inline-item-after {
margin-left: 0.25rem;
}

.table, table.docutils {
border-spacing: 0;
color: #212529;
margin-bottom: 0;
width: 100%;
}
.table thead, table.docutils thead {
background-color: #fff;
}
.table thead th, table.docutils thead th,
.table thead td,
table.docutils thead td {
background-color: #fff;
border-bottom: calc(2 * 0.0625rem) solid #dee2e6;
border-top-width: 0px;
vertical-align: bottom;
}
.table th:first-child, table.docutils th:first-child,
.table td:first-child,
table.docutils td:first-child,
.table .table-column-start,
table.docutils .table-column-start {
padding-left: 15px;
}
.table th:last-child, table.docutils th:last-child,
.table td:last-child,
table.docutils td:last-child,
.table .table-column-end,
table.docutils .table-column-end {
padding-right: 15px;
}
.table th, table.docutils th {
background-clip: padding-box;
border-top: 0.0625rem solid #dee2e6;
color: #495057;
height: 36px;
padding: 0.75rem;
position: relative;
vertical-align: top;
}
.table td, table.docutils td {
background-clip: padding-box;
border-bottom-width: 0.0625rem;
border-color: #dee2e6;
border-left-width: 0px;
border-right-width: 0px;
border-style: solid;
border-top-width: 0.0625rem;
padding: 0.75rem;
position: relative;
vertical-align: middle;
}
.table tbody + tbody, table.docutils tbody + tbody {
border-top: calc(2 * 0.0625rem) solid #dee2e6;
}
.table caption, table.docutils caption {
caption-side: top;
padding-left: 0.75rem;
padding-right: 0.75rem;
}
.table .table-divider th, table.docutils .table-divider th,
.table .table-divider td,
table.docutils .table-divider td {
background-color: #fff;
padding: 0.75rem;
}
.table .table-active, table.docutils .table-active {
background-color: #ececec;
}
.table .table-active th, table.docutils .table-active th,
.table .table-active td,
table.docutils .table-active td {
background-color: #ececec;
}
.table .table-active .quick-action-menu, table.docutils .table-active .quick-action-menu {
background-color: #ececec;
}
.table .table-disabled, table.docutils .table-disabled {
color: #acacac;
}
.table .table-disabled th, table.docutils .table-disabled th,
.table .table-disabled td,
table.docutils .table-disabled td {
background-color: #fff;
cursor: not-allowed;
}
.table .table-disabled th [href], table.docutils .table-disabled th [href],
.table .table-disabled td [href],
table.docutils .table-disabled td [href] {
color: #acacac;
pointer-events: none;
}
.table .table-disabled .table-title, table.docutils .table-disabled .table-title {
color: #acacac;
}
.table .table-disabled .table-list-title, table.docutils .table-disabled .table-list-title {
color: #acacac;
}
.table .autofit-col, table.docutils .autofit-col {
justify-content: center;
padding-left: 0.75rem;
padding-right: 0.75rem;
}
.table .autofit-col:first-child, table.docutils .autofit-col:first-child {
padding-left: 0;
}
.table .autofit-col:last-child, table.docutils .autofit-col:last-child {
padding-right: 0;
}
.table .custom-control, table.docutils .custom-control,
.table .form-check,
table.docutils .form-check {
margin-bottom: 0;
}
.table .quick-action-menu, table.docutils .quick-action-menu {
align-items: flex-start;
padding-bottom: 0.75rem;
padding-top: 0.75rem;
}

.table-caption-bottom caption {
caption-side: bottom;
}

.table-sm th,
.table-sm td {
padding: 0.3rem;
}
.table-bordered {
border: 0.0625rem solid #dee2e6;
}
.table-bordered thead th,
.table-bordered thead td {
border-bottom-width: calc(2 * 0.0625rem);
}
.table-bordered th,
.table-bordered td {
border: 0.0625rem solid #dee2e6;
}
.table-borderless th,
.table-borderless td,
.table-borderless thead th,
.table-borderless tbody + tbody {
border: 0;
}

.table-striped tbody tr:nth-of-type(odd) td, table.docutils tbody tr:nth-of-type(odd) td,
.table-striped tbody tr:nth-of-type(odd) th,
table.docutils tbody tr:nth-of-type(odd) th {
background-color: #f2f2f2;
}

.table-hover tbody tr:hover {
background-color: #ececec;
color: #212529;
}
.table-hover tbody tr:hover th,
.table-hover tbody tr:hover td {
background-color: #ececec;
color: #212529;
}
.table-hover tbody tr:hover .quick-action-menu {
background-color: #ececec;
}
.table-hover .table-active:hover .quick-action-menu {
background-color: #ececec;
}
.table-hover .table-disabled:hover {
background-color: #fff;
}
.table-hover .table-disabled:hover th,
.table-hover .table-disabled:hover td {
background-color: #fff;
}
.table-primary,
.table-primary > th,
.table-primary > td {
background-color: #b8daff;
border-color: #7abaff;
}
.table-primary th,
.table-primary td,
.table-primary thead th,
.table-primary tbody + tbody {
border-color: #7abaff;
}

.table-hover .table-primary:hover {
background-color: #9fcdff;
}
.table-hover .table-primary:hover > td,
.table-hover .table-primary:hover > th {
background-color: #9fcdff;
}

.table-secondary,
.table-secondary > th,
.table-secondary > td {
background-color: #d6d8db;
border-color: #b3b7bb;
}
.table-secondary th,
.table-secondary td,
.table-secondary thead th,
.table-secondary tbody + tbody {
border-color: #b3b7bb;
}

.table-hover .table-secondary:hover {
background-color: #c8cbcf;
}
.table-hover .table-secondary:hover > td,
.table-hover .table-secondary:hover > th {
background-color: #c8cbcf;
}

.table-success,
.table-success > th,
.table-success > td {
background-color: #c3e6cb;
border-color: #8fd19e;
}
.table-success th,
.table-success td,
.table-success thead th,
.table-success tbody + tbody {
border-color: #8fd19e;
}

.table-hover .table-success:hover {
background-color: #b1dfbb;
}
.table-hover .table-success:hover > td,
.table-hover .table-success:hover > th {
background-color: #b1dfbb;
}

.table-info,
.table-info > th,
.table-info > td {
background-color: #bee5eb;
border-color: #86cfda;
}
.table-info th,
.table-info td,
.table-info thead th,
.table-info tbody + tbody {
border-color: #86cfda;
}

.table-hover .table-info:hover {
background-color: #abdde5;
}
.table-hover .table-info:hover > td,
.table-hover .table-info:hover > th {
background-color: #abdde5;
}

.table-warning,
.table-warning > th,
.table-warning > td {
background-color: #ffeeba;
border-color: #ffdf7e;
}
.table-warning th,
.table-warning td,
.table-warning thead th,
.table-warning tbody + tbody {
border-color: #ffdf7e;
}

.table-hover .table-warning:hover {
background-color: #ffe8a1;
}
.table-hover .table-warning:hover > td,
.table-hover .table-warning:hover > th {
background-color: #ffe8a1;
}

.table-danger,
.table-danger > th,
.table-danger > td {
background-color: #f5c6cb;
border-color: #ed969e;
}
.table-danger th,
.table-danger td,
.table-danger thead th,
.table-danger tbody + tbody {
border-color: #ed969e;
}

.table-hover .table-danger:hover {
background-color: #f1b0b7;
}
.table-hover .table-danger:hover > td,
.table-hover .table-danger:hover > th {
background-color: #f1b0b7;
}

.table-light,
.table-light > th,
.table-light > td {
background-color: #fdfdfe;
border-color: #fbfcfc;
}
.table-light th,
.table-light td,
.table-light thead th,
.table-light tbody + tbody {
border-color: #fbfcfc;
}

.table-hover .table-light:hover {
background-color: #ececf6;
}
.table-hover .table-light:hover > td,
.table-hover .table-light:hover > th {
background-color: #ececf6;
}

.table-dark,
.table-dark > th,
.table-dark > td {
background-color: #c6c8ca;
border-color: #95999c;
}
.table-dark th,
.table-dark td,
.table-dark thead th,
.table-dark tbody + tbody {
border-color: #95999c;
}

.table-hover .table-dark:hover {
background-color: #b9bbbe;
}
.table-hover .table-dark:hover > td,
.table-hover .table-dark:hover > th {
background-color: #b9bbbe;
}

.table-striped tbody .table-disabled:nth-of-type(odd) td, table.docutils tbody .table-disabled:nth-of-type(odd) td,
.table-striped tbody .table-disabled:nth-of-type(odd) th,
table.docutils tbody .table-disabled:nth-of-type(odd) th {
background-color: #fff;
}

.table .thead-dark th, table.docutils .thead-dark th {
background-color: #343a40;
border-color: #454d55;
color: #fff;
}
.table .thead-light th, table.docutils .thead-light th {
background-color: #fff;
border-color: #dee2e6;
color: #495057;
}

.table-dark {
background-color: #343a40;
color: #fff;
}
.table-dark th,
.table-dark td,
.table-dark thead th {
border-color: #454d55;
}
.table-dark.table-bordered {
border-width: 0;
}
.table-dark.table-striped tbody tr:nth-of-type(odd), table.table-dark.docutils tbody tr:nth-of-type(odd) {
background-color: rgba(255, 255, 255, 0.05);
}
.table-dark.table-hover tbody tr:hover {
background-color: rgba(255, 255, 255, 0.075);
color: #fff;
}

.table-title {
font-size: 1rem;
font-weight: 500;
line-height: 1.5;
margin-bottom: 0;
}
.table-title[href],
.table-title [href] {
color: #212529;
}
.table-title[href]:hover,
.table-title [href]:hover {
color: #212529;
}
.table-link {
color: #495057;
}
.table-link:hover {
color: #262a2d;
}
.table-action-link {
align-items: center;
border-radius: 0.25rem;
display: inline-flex;
height: 2rem;
justify-content: center;
vertical-align: middle;
width: 2rem;
}
.table-action-link:hover {
text-decoration: none;
}
.table-action-link .lexicon-icon {
margin-top: 0;
}

.table-responsive-sm {
margin-bottom: 1.5rem;
}
@media (max-width: 575.98px) {
.table-responsive-sm {
	display: block;
	-webkit-overflow-scrolling: touch;
	overflow-x: auto;
	width: 100%;
}
}
.table-responsive-md {
margin-bottom: 1.5rem;
}
@media (max-width: 767.98px) {
.table-responsive-md {
	display: block;
	-webkit-overflow-scrolling: touch;
	overflow-x: auto;
	width: 100%;
}
}
.table-responsive-lg {
margin-bottom: 1.5rem;
}
@media (max-width: 991.98px) {
.table-responsive-lg {
	display: block;
	-webkit-overflow-scrolling: touch;
	overflow-x: auto;
	width: 100%;
}
}
.table-responsive-xl {
margin-bottom: 1.5rem;
}
@media (max-width: 1199.98px) {
.table-responsive-xl {
	display: block;
	-webkit-overflow-scrolling: touch;
	overflow-x: auto;
	width: 100%;
}
}
.table-responsive {
margin-bottom: 1.5rem;
display: block;
-webkit-overflow-scrolling: touch;
overflow-x: auto;
width: 100%;
}

.table-list {
border-collapse: separate;
border-color: #dee2e6;
border-radius: 0.25rem;
border-style: solid;
border-width: 0.0625rem 0.0625rem;
margin-bottom: 0.0625rem;
}
.table-list thead {
background-color: #fff;
border-top-left-radius: calc(0.25rem - 0.0625rem);
border-top-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list thead th,
.table-list thead td {
background-color: #fff;
border-bottom-width: 0;
}
.table-list th,
.table-list td {
border-color: #dee2e6;
border-style: solid;
border-width: 0.0625rem 0 0 0;
}
.table-list thead:first-child tr:first-child th,
.table-list thead:first-child tr:first-child td,
.table-list tbody:first-child tr:first-child th,
.table-list tbody:first-child tr:first-child td,
.table-list tfoot:first-child tr:first-child th,
.table-list tfoot:first-child tr:first-child td,
.table-list caption:first-child + thead tr:first-child th,
.table-list caption:first-child + thead tr:first-child td {
border-top-width: 0;
}
.table-list thead:first-child tr:first-child th:first-child,
.table-list thead:first-child tr:first-child td:first-child,
.table-list tbody:first-child tr:first-child th:first-child,
.table-list tbody:first-child tr:first-child td:first-child,
.table-list tfoot:first-child tr:first-child th:first-child,
.table-list tfoot:first-child tr:first-child td:first-child,
.table-list caption:first-child + thead tr:first-child th:first-child,
.table-list caption:first-child + thead tr:first-child td:first-child {
border-top-left-radius: calc(0.25rem - 0.0625rem);
}
.table-list thead:first-child tr:first-child th:last-child,
.table-list thead:first-child tr:first-child td:last-child,
.table-list tbody:first-child tr:first-child th:last-child,
.table-list tbody:first-child tr:first-child td:last-child,
.table-list tfoot:first-child tr:first-child th:last-child,
.table-list tfoot:first-child tr:first-child td:last-child,
.table-list caption:first-child + thead tr:first-child th:last-child,
.table-list caption:first-child + thead tr:first-child td:last-child {
border-top-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list .table-row-start .table-cell-start {
border-top-left-radius: calc(0.25rem - 0.0625rem);
}
.table-list .table-row-start .table-cell-end {
border-top-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list thead:last-child tr:last-child th:first-child,
.table-list thead:last-child tr:last-child td:first-child,
.table-list tbody:last-child tr:last-child th:first-child,
.table-list tbody:last-child tr:last-child td:first-child,
.table-list tfoot:last-child tr:last-child th:first-child,
.table-list tfoot:last-child tr:last-child td:first-child {
border-bottom-left-radius: calc(0.25rem - 0.0625rem);
}
.table-list thead:last-child tr:last-child th:last-child,
.table-list thead:last-child tr:last-child td:last-child,
.table-list tbody:last-child tr:last-child th:last-child,
.table-list tbody:last-child tr:last-child td:last-child,
.table-list tfoot:last-child tr:last-child th:last-child,
.table-list tfoot:last-child tr:last-child td:last-child {
border-bottom-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list .table-row-end .table-cell-start {
border-bottom-left-radius: calc(0.25rem - 0.0625rem);
}
.table-list .table-row-end .table-cell-end {
border-bottom-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list tbody {
background-color: #fff;
border-bottom-left-radius: calc(0.25rem - 0.0625rem);
border-bottom-right-radius: calc(0.25rem - 0.0625rem);
}
.table-list tbody th,
.table-list tbody td {
background-color: #fff;
vertical-align: middle;
}
.table-list tfoot {
background-color: #fff;
}
.table-list tfoot th,
.table-list tfoot td {
background-color: #fff;
vertical-align: middle;
}
.table-list .table-divider th,
.table-list .table-divider td {
padding-bottom: 0.75rem;
padding-left: 0.75rem;
padding-right: 0.75rem;
padding-top: 0.75rem;
}
.table-list .table-active {
background-color: #dadada;
}
.table-list .table-active th,
.table-list .table-active td {
background-color: #dadada;
}
.table-list .table-active .quick-action-menu {
background-color: #dadada;
}
.table-list .table-disabled {
background-color: #fff;
color: #acacac;
}
.table-list .table-disabled th,
.table-list .table-disabled td {
background-color: #fff;
color: #acacac;
}
.table-list .table-disabled th [href],
.table-list .table-disabled td [href] {
color: #acacac;
pointer-events: none;
}
.table-list .table-disabled .table-title {
color: #acacac;
}
.table-list .table-disabled .table-list-title {
color: #acacac;
}
.table-list .quick-action-menu {
align-items: center;
background-color: #fff;
bottom: 0;
top: 0;
}

.table.table-list.table-bordered thead th, table.table-list.table-bordered.docutils thead th,
.table.table-list.table-bordered thead td,
table.table-list.table-bordered.docutils thead td {
border-bottom-width: 0;
}
.table.table-list.table-bordered th, table.table-list.table-bordered.docutils th,
.table.table-list.table-bordered td,
table.table-list.table-bordered.docutils td {
border-left-width: 0.0625rem;
}
.table.table-list.table-bordered th:first-child, table.table-list.table-bordered.docutils th:first-child,
.table.table-list.table-bordered td:first-child,
table.table-list.table-bordered.docutils td:first-child,
.table.table-list.table-bordered .table-column-start,
table.table-list.table-bordered.docutils .table-column-start {
border-left-width: 0;
}
.table-list.table-striped tbody tr:nth-of-type(odd) td, table.table-list.docutils tbody tr:nth-of-type(odd) td,
.table-list.table-striped tbody tr:nth-of-type(odd) th,
table.table-list.docutils tbody tr:nth-of-type(odd) th {
background-color: #f2f2f2;
}

.table-list.table-hover tbody tr:hover {
background-color: #ececec;
}
.table-list.table-hover tbody tr:hover th,
.table-list.table-hover tbody tr:hover td {
background-color: #ececec;
}
.table-list.table-hover tbody tr:hover .quick-action-menu {
background-color: #ececec;
}
.table-list.table-hover .table-active:hover {
background-color: #dadada;
}
.table-list.table-hover .table-active:hover th,
.table-list.table-hover .table-active:hover td {
background-color: #dadada;
}
.table-list.table-hover .table-active:hover .quick-action-menu {
background-color: #ececec;
}
.table-list.table-hover .table-disabled {
background-color: #fff;
}
.table-list.table-hover .table-disabled:hover {
background-color: #fff;
}
.table-list.table-hover .table-disabled:hover th,
.table-list.table-hover .table-disabled:hover td {
background-color: #fff;
}
.table-list.table-striped tbody .table-disabled:nth-of-type(odd), table.table-list.docutils tbody .table-disabled:nth-of-type(odd),
.table-list.table-striped tbody .table-disabled:nth-of-type(odd) td,
table.table-list.docutils tbody .table-disabled:nth-of-type(odd) td,
.table-list.table-striped tbody .table-disabled:nth-of-type(odd) th,
table.table-list.docutils tbody .table-disabled:nth-of-type(odd) th {
background-color: #fff;
}

.table-list-title {
font-size: 1rem;
font-weight: 500;
line-height: 1.2;
margin-bottom: 0;
}
.table-list-title[href],
.table-list-title [href] {
color: #212529;
}
.table-list-title[href]:hover,
.table-list-title [href]:hover {
color: #212529;
}
.table-list-link {
color: #495057;
}
.table-list-link:hover {
color: #262a2d;
}
.table-list-action-link {
align-items: center;
display: inline-flex;
height: 2rem;
justify-content: center;
vertical-align: middle;
width: 2rem;
}
.table-list-action-link:hover {
text-decoration: none;
}
.table-list-action-link .lexicon-icon {
margin-top: 0;
}

.table-nowrap td,
.table-nowrap th {
white-space: nowrap;
}

.table-heading-nowrap thead td,
.table-heading-nowrap thead th {
white-space: nowrap;
}

.table-valign-bottom tbody td,
.table-valign-bottom tbody th,
.table-valign-bottom tfoot td,
.table-valign-bottom tfoot th,
.table-valign-bottom thead td,
.table-valign-bottom thead th {
vertical-align: bottom;
}
.table-valign-bottom tbody td,
.table-valign-bottom tbody th {
padding-bottom: 1rem;
}
.table-valign-bottom.show-quick-actions-on-hover .quick-action-menu {
align-items: flex-end;
}

.table-valign-middle tbody td,
.table-valign-middle tbody th,
.table-valign-middle tfoot td,
.table-valign-middle tfoot th,
.table-valign-middle thead td,
.table-valign-middle thead th {
vertical-align: middle;
}

.table-valign-top tbody td,
.table-valign-top tbody th,
.table-valign-top tfoot td,
.table-valign-top tfoot th,
.table-valign-top thead td,
.table-valign-top thead th {
vertical-align: top;
}
.table-valign-top tbody td,
.table-valign-top tbody th {
padding-top: 1rem;
}
.table-valign-top.show-quick-actions-on-hover .quick-action-menu {
align-items: flex-start;
}

.tbody-valign-bottom tbody td,
.tbody-valign-bottom tbody th {
padding-bottom: 1rem;
vertical-align: bottom;
}
.tbody-valign-bottom.show-quick-actions-on-hover .quick-action-menu {
align-items: flex-end;
}

.tbody-valign-middle tbody td {
vertical-align: middle;
}

.tbody-valign-top tbody td,
.tbody-valign-top tbody th {
padding-top: 1rem;
vertical-align: top;
}
.tbody-valign-top.show-quick-actions-on-hover .quick-action-menu {
align-items: flex-start;
}

.thead-valign-bottom thead td,
.thead-valign-bottom thead th {
vertical-align: bottom;
}

.thead-valign-middle thead td,
.thead-valign-middle thead th {
vertical-align: middle;
}

.thead-valign-top thead td,
.thead-valign-top thead th {
vertical-align: top;
}

.show-quick-actions-on-hover tr:not(.table-active):not(.table-disabled):hover .quick-action-menu {
display: flex;
}
.show-quick-actions-on-hover .table-focus:not(.table-active):not(.table-disabled) .quick-action-menu {
display: flex;
}

.table-striped tbody tr:nth-of-type(odd) .quick-action-menu, table.docutils tbody tr:nth-of-type(odd) .quick-action-menu {
background-color: #f2f2f2;
}
.table-striped tbody .table-active:nth-of-type(odd) .quick-action-menu, table.docutils tbody .table-active:nth-of-type(odd) .quick-action-menu {
background-color: #f2f2f2;
}

.table-list.table-striped tbody tr:nth-of-type(odd) .quick-action-menu, table.table-list.docutils tbody tr:nth-of-type(odd) .quick-action-menu {
background-color: #f2f2f2;
}
.table-list.table-striped tbody .table-active:nth-of-type(odd) .quick-action-menu, table.table-list.docutils tbody .table-active:nth-of-type(odd) .quick-action-menu {
background-color: #dadada;
}

.table-column-text-start,
.table-cell-text-start {
text-align: left;
}

.table-column-text-center,
.table-cell-text-center {
text-align: center;
}

.table-column-text-end,
.table-cell-text-end {
text-align: right;
}

.table-column,
.table-cell-contract, .table-autofit td, table.docutils td,
.table-autofit th,
table.docutils th {
display: table-cell;
overflow-wrap: break-word;
word-wrap: break-word;
width: 1%;
}

.table-cell-expand,
.table-column-expand, .table-autofit .table-cell-expand, table.docutils .table-cell-expand {
display: table-cell;
max-width: 12.5rem;
min-width: 12.5rem;
overflow-wrap: break-word;
word-wrap: break-word;
width: auto;
}

.table-cell-expand-small,
.table-column-expand-small, .table-autofit .table-cell-expand-small, table.docutils .table-cell-expand-small {
max-width: 12.5rem;
overflow-wrap: break-word;
word-wrap: break-word;
width: 25%;
}

.table-cell-expand-smaller,
.table-column-expand-smaller, .table-autofit .table-cell-expand-smaller, table.docutils .table-cell-expand-smaller {
max-width: 12.5rem;
overflow-wrap: break-word;
word-wrap: break-word;
width: 15%;
}

.table-cell-expand-smallest,
.table-column-expand-smallest, .table-autofit .table-cell-expand-smallest, table.docutils .table-cell-expand-smallest {
max-width: 12.5rem;
overflow-wrap: break-word;
word-wrap: break-word;
width: 10%;
}

.table-cell-minw-50,
.table-column-minw-50 {
min-width: 50px;
}

.table-cell-minw-75,
.table-column-minw-75 {
min-width: 75px;
}

.table-cell-minw-100,
.table-column-minw-100 {
min-width: 100px;
}

.table-cell-minw-150,
.table-column-minw-150 {
min-width: 150px;
}

.table-cell-minw-200,
.table-column-minw-200 {
min-width: 200px;
}

.table-cell-minw-250,
.table-column-minw-250 {
min-width: 250px;
}

.table-cell-minw-300,
.table-column-minw-300 {
min-width: 300px;
}

.table-cell-minw-350,
.table-column-minw-350 {
min-width: 350px;
}

.table-cell-minw-400,
.table-column-minw-400 {
min-width: 400px;
}

.table-cell-ws-normal,
.table-column-ws-normal {
white-space: normal;
}

.table-cell-ws-nowrap,
.table-column-ws-nowrap {
white-space: nowrap;
}

.table-img {
height: auto;
max-height: 100px;
max-width: none;
width: auto;
}

.not-found {
text-align: center;
}
.not-found.main-content {
margin-top: 5.3125rem;
}
.not-found svg {
width: 55%;
}

.not-found-message {
background-color: #f4f6f9;
margin-top: -10%;
padding-bottom: 5%;
padding-top: 10%;
}

div.admonition {
border-bottom-right-radius: 0.25rem;
border-top-right-radius: 0.25rem;
margin: 1rem 0;
padding: 0.75rem 1.5rem 1rem 2.625rem;
}
div.admonition.error {
background-color: #feefef;
border-left: 3px solid #d33a2f;
}
div.admonition.error .admonition-title {
color: #da1414;
}
div.admonition.error .admonition-title:before {
content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0yLjA1IDExLjk1QTYuOTUgNi45NSAwIDAwNyAxNGMxLjg3IDAgMy42MjktLjcyNyA0Ljk1LTIuMDVBNi45NSA2Ljk1IDAgMDAxNCA3YzAtMS44Ny0uNzI3LTMuNjI5LTIuMDUtNC45NUE2Ljk0NCA2Ljk0NCAwIDAwNyAwQzUuMTMgMCAzLjM3MS43MjcgMi4wNSAyLjA1QTYuOTQ1IDYuOTQ1IDAgMDAwIDdjMCAxLjg3LjcyOCAzLjYyOSAyLjA1IDQuOTV6TTYuMTI2IDMuNWEuODc0Ljg3NCAwIDExMS43NSAwdjQuMzc1YS44NzQuODc0IDAgMTEtMS43NSAwVjMuNXptMS43NSA3YS44NzQuODc0IDAgMTAtMS43NSAwIC44NzQuODc0IDAgMTAxLjc1IDB6IiBmaWxsPSIjREExNDE0Ii8+PC9zdmc+);
}
div.admonition.important {
background-color: #eef2fa;
border-left: 3px solid #89a7e0;
}
div.admonition.important .admonition-title {
color: #2e5aac;
}
div.admonition.important .admonition-title:before {
content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTQiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xMS45NSAyLjA1QTYuOTU1IDYuOTU1IDAgMDA3IDBDNS4xMyAwIDMuMzcyLjcyOCAyLjA1IDIuMDVBNi45NTcgNi45NTcgMCAwMDAgN2MwIDEuODcuNzI5IDMuNjI4IDIuMDUgNC45NUE2Ljk1NSA2Ljk1NSAwIDAwNyAxNGMxLjg3IDAgMy42MjgtLjcyOCA0Ljk1LTIuMDVBNi45NTcgNi45NTcgMCAwMDE0IDdjMC0xLjg3LS43MjktMy42MjgtMi4wNS00Ljk1ek03Ljg3NSAxMC41YS44NzUuODc1IDAgMTEtMS43NSAwVjYuMTI1YS44NzUuODc1IDAgMTExLjc1IDBWMTAuNXptLTEuNzUtN2EuODc1Ljg3NSAwIDEwMS43NSAwIC44NzUuODc1IDAgMDAtMS43NSAweiIgZmlsbD0iIzJFNUFBQyIvPjwvc3ZnPg==);
}
div.admonition.note {
background-color: #edf9f0;
border-left: 3px solid #5aca74;
}
div.admonition.note .admonition-title {
color: #287d3c;
}
div.admonition.note .admonition-title:before {
content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik0xNS4yMDYuNzk0QTIuNjg3IDIuNjg3IDAgMDAxMy4yOTUgMGMtLjcyIDAtMS40LjI4My0xLjkxMS43OUwuMDAzIDEyLjE3MWwuMTQgMy42NiAzLjgwNi4wMzlMMTUuMjA2IDQuNjE2YTIuNzA2IDIuNzA2IDAgMDAwLTMuODIyek0zLjIxIDE0LjA2NGwtMS4zMzMtLjAxMy0uMDQ1LTEuMTU5IDguNzExLTguNzExIDEuMjczIDEuMjczLTguNjA2IDguNjF6bTkuODg3LTkuODg2bC44MzgtLjgzOGEuOTA2LjkwNiAwIDAwMC0xLjI3My45MjMuOTIzIDAgMDAtMS4yNzQgMGwtLjgzOC44MzggMS4yNzQgMS4yNzN6IiBmaWxsPSIjMjg3RDNDIi8+PC9zdmc+);
}
div.admonition.tip {
background-color: #eafaf7;
border-left: 3px solid #50d2a0;
}
div.admonition.tip .admonition-title {
color: #0b7543;
}
div.admonition.tip .admonition-title:before {
content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTIiIGhlaWdodD0iMTYiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZD0iTTEwLjg5MyA4LjAyNmwtMy43NjYtMi4xOCAxLjktNC41NjZDOS40NDYuMjQ1IDguMjQ2LS4zNjQgNy42MTUuMjM5TC45NTUgNi41NDVjLS40MTguNDg1LS4zODcgMS4wMTYuMTY2IDEuNDE2bDMuNzcyIDIuMTktMS45MjUgNC42MTZjLS4zNTYuOTQuNzM3IDEuNTc1IDEuNDM0Ljk5NGw2LjY2My02LjMwNmMuNDEtLjQ2Ni4zNDctMS4wNzItLjE3Mi0xLjQyOXoiIGZpbGw9IiMwQjc1NDMiLz48L3N2Zz4=);
}
div.admonition.warning {
background-color: #fff4ec;
border-left: 3px solid #ff8f39;
}
div.admonition.warning .admonition-title {
color: #b95000;
}
div.admonition.warning .admonition-title:before {
content: url(data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMTQiIGhlaWdodD0iMTIiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+PHBhdGggZmlsbC1ydWxlPSJldmVub2RkIiBjbGlwLXJ1bGU9ImV2ZW5vZGQiIGQ9Ik03Ljg3NSA1LjE0M3YxLjcxNEEuODY2Ljg2NiAwIDAxNyA3LjcxNGEuODY2Ljg2NiAwIDAxLS44NzUtLjg1N1Y1LjE0MyA0LjA3YzAtLjQ3My4zOTItLjg1Ny44NzUtLjg1N3MuODc1LjM4NC44NzUuODU3djEuMDcyek03IDEwLjVhLjg2Ni44NjYgMCAwMS0uODc1LS44NTdjMC0uNDczLjM5Mi0uODU3Ljg3NS0uODU3cy44NzUuMzg0Ljg3NS44NTdBLjg2Ni44NjYgMCAwMTcgMTAuNXptNi44NDQtLjM5NEw3Ljk5Mi42MzJDNy43ODQuMjM2IDcuNDEzIDAgNyAwYy0uNDEzIDAtLjc4NC4yMzYtLjk5Mi42MzJMLjE1NiAxMC4xMDZhMS4zNTMgMS4zNTMgMCAwMDAgMS4yNjJjLjIwOC4zOTYuNTc5LjYzMi45OTIuNjMyaDExLjcwNGMuNDEzIDAgLjc4NC0uMjM2Ljk5Mi0uNjMyYTEuMzUzIDEuMzUzIDAgMDAwLTEuMjYyeiIgZmlsbD0iI0I5NTAwMCIvPjwvc3ZnPg==);
}
div.admonition .admonition-title {
margin-bottom: 0.25rem;
text-transform: uppercase;
}
div.admonition .admonition-title:before {
background-size: 0.875rem 0.875rem;
display: inline-block;
height: 0.875rem;
margin-left: -1.625rem;
margin-right: 0.75rem;
width: 0.875rem;
}

.breadcrumb {
display: inline-flex;
flex-wrap: wrap;
list-style: none;
margin: 0;
padding: 0;
}
.breadcrumb li {
color: #6c757d;
font-size: 0.875rem;
font-weight: 600;
margin-right: 0.5rem;
position: relative;
}
.breadcrumb li + li {
padding-left: 1rem;
}
.breadcrumb li + li::before {
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M375.2%20239.2%20173.3%2037c-23.6-23-59.9%2011.9-36%2035.1l183%20183.9-182.9%20183.8c-24%2023.5%2012.5%2058.2%2036.1%2035.2l201.7-202.1c10.2-10.1%209.3-24.4%200-33.7z'%20fill=''/%3E%3C/svg%3E");
background-repeat: no-repeat;
background-size: 100%;
content: "";
display: block;
float: left;
height: 0.75em;
left: 0;
margin-top: -0.375em;
padding: 0;
position: absolute;
top: 50%;
width: 0.75em;
}
.breadcrumb li a {
color: #30313f;
}
.breadcrumb li a:hover {
text-decoration: none;
}

.breadcrumb-wrapper {
padding: 0;
}

.btn {
cursor: pointer;
font-weight: 600;
}
.btn.btn-primary, .btn.btn-primary:active, .btn.btn-primary:focus {
background-color: #fff;
border-color: #0b5fff;
color: #0b5fff;
}
.btn.btn-primary:hover {
background: #eff3fd;
box-shadow: 0 0.125rem 0.375rem 0 rgba(2, 19, 51, 0.15);
}
.btn.btn-secondary, .btn.btn-secondary:focus, .btn.btn-secondary:hover, .btn.btn-secondary:not(:disabled):not(.disabled):active {
background-color: #eff3fd;
border-color: #0b5fff;
color: #30313f;
}
.btn.btn-secondary:active, .btn.btn-secondary:focus, .btn.btn-secondary:hover, .btn.btn-secondary:not(:disabled):not(.disabled):active {
box-shadow: 0 0.125rem 0.375rem 0 rgba(2, 19, 51, 0.15);
}

a.copybtn {
cursor: pointer;
height: 26px;
left: initial;
opacity: 0;
right: 6px;
top: 6px;
transition: none;
width: 26px;
}
a.copybtn > img {
background-color: #373f52;
border-radius: 4px;
border: 1px dashed rgba(255, 255, 255, 0.2);
box-shadow: 0 6px 12px rgba(0, 0, 0, 0.55);
box-sizing: border-box;
display: block;
height: 26px;
padding: 4px;
width: 26px;
}

li .copybtn img {
margin: 0;
}

button.copybtn,
.highlight button.copybtn:hover {
background-color: #272833;
}

.o-tooltip--left {
position: relative;
}
.o-tooltip--left:after {
background-color: #272833;
border-radius: 4px;
box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.4);
color: #fff;
content: attr(data-tooltip);
font-size: 14px;
left: 50%;
max-width: 230px;
opacity: 0;
padding: 12px;
pointer-events: none;
position: absolute;
text-align: center;
top: 100%;
transform: translateX(-50%) translateY(4px);
transition: none;
visibility: hidden;
white-space: nowrap;
z-index: 2;
}
.o-tooltip--left:hover:after {
display: block;
opacity: 1;
transform: translateX(-50%) translateY(4px);
visibility: visible;
}

.documentations .article-body {
margin-top: 1.5rem;
}
@media (min-width: 768px) {
.documentations .article-body {
	margin-bottom: 5rem;
	padding: 1.5rem 8.33333%;
}
}
.documentations .doc-body,
.documentations .doc-nav-wrapper,
.documentations .doc-nav-wrapper-inner {
padding: 0;
}
@media (min-width: 768px) {
.documentations .doc-content {
	margin-top: 4.8125rem;
}
}
.documentations .doc-nav,
.documentations .doc-nav-footer,
.documentations .doc-nav-wrapper-inner {
background-color: #f4f6f9;
}
.documentations .doc-nav {
font-weight: 600;
height: calc(100% - 8.3125rem);
overflow: auto;
padding: 1rem 0;
}
.documentations .doc-nav > ul {
margin: 0;
}
.documentations .doc-nav a {
border-left: 3px solid transparent;
color: #6b6c7e;
display: block;
padding: 0.5rem 1.5rem 0.5rem 1rem;
}
.documentations .doc-nav a:hover {
text-decoration: none;
}
.documentations .doc-nav ul {
list-style: none;
padding: 0;
}
.documentations .doc-nav .back-link {
align-items: center;
display: flex;
padding-left: 1rem;
width: 100%;
}
.documentations .doc-nav .back-link svg {
fill: #6b6c7e;
height: 1.5rem;
padding-right: 0.75rem;
width: 1.5rem;
}
.documentations .doc-nav .toctree-l1.active a {
color: #0b5fff;
}
.documentations .doc-nav .toctree-l1.current > a {
background-image: none;
border-left-color: #0b5fff;
}
.documentations .doc-nav .toctree-l1 > a {
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M375.2%20239.2%20173.3%2037c-23.6-23-59.9%2011.9-36%2035.1l183%20183.9-182.9%20183.8c-24%2023.5%2012.5%2058.2%2036.1%2035.2l201.7-202.1c10.2-10.1%209.3-24.4%200-33.7z'%20fill='%23000'/%3E%3C/svg%3E");
background-position: right 0.8rem top 1rem;
background-repeat: no-repeat;
background-size: 0.65rem;
color: #000;
}
.documentations .doc-nav .toctree-l2,
.documentations .doc-nav .toctree-l3,
.documentations .doc-nav .toctree-l4,
.documentations .doc-nav .toctree-l5,
.documentations .doc-nav .toctree-l6 {
font-size: 0.875rem;
}
.documentations .doc-nav .toctree-l2.current.parent-level > a,
.documentations .doc-nav .toctree-l3.current.parent-level > a,
.documentations .doc-nav .toctree-l4.current.parent-level > a,
.documentations .doc-nav .toctree-l5.current.parent-level > a,
.documentations .doc-nav .toctree-l6.current.parent-level > a {
background-image: none;
border-left-color: #0b5fff;
color: inherit;
font-size: initial;
padding: 0.5rem 1.5rem 0.5rem 1rem;
}
.documentations .doc-nav .toctree-l2.current > a,
.documentations .doc-nav .toctree-l3.current > a,
.documentations .doc-nav .toctree-l4.current > a,
.documentations .doc-nav .toctree-l5.current > a,
.documentations .doc-nav .toctree-l6.current > a {
color: #0b5fff;
}
.documentations .doc-nav .toctree-l2 > a,
.documentations .doc-nav .toctree-l3 > a,
.documentations .doc-nav .toctree-l4 > a,
.documentations .doc-nav .toctree-l5 > a,
.documentations .doc-nav .toctree-l6 > a {
padding: 0.5rem 2rem;
}
.documentations .doc-nav-wrapper.mobile-nav-hide .collapse-btn {
display: none;
}
.documentations .doc-nav-wrapper.mobile-nav-hide .expand-btn {
display: block;
}
@media (max-width: 767.98px) {
.documentations .doc-nav-wrapper.mobile-nav-hide .doc-nav {
	display: none;
}
}
.documentations .doc-nav-wrapper .collapse-btn,
.documentations .doc-nav-wrapper .doc-nav {
display: block;
}
.documentations .doc-nav-wrapper .expand-btn {
display: none;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer {
align-items: center;
border-top: 1px solid #dadee3;
bottom: 0;
height: 3.5rem;
padding: 0 0.75rem;
position: absolute;
width: 100%;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown {
width: 100%;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown button {
color: #6b6c7e;
font-size: 0.875rem;
padding: 0.25rem;
width: 100%;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown button svg {
margin-right: 4px;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown .dropdown-menu {
width: calc(100% - 1.5rem);
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown .dropdown-menu li {
padding: 0;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown .dropdown-menu .dropdown-item {
display: block;
padding: 0.375rem 0.75rem;
}
.documentations .doc-nav-wrapper-inner .doc-nav-footer .dropdown .dropdown-menu .dropdown-item:hover {
text-decoration: none;
}
@media (min-width: 768px) {
.documentations .doc-nav-wrapper-inner {
	height: calc(100vh - 3.5rem);
	max-width: 16.66667%;
	position: fixed;
	width: 100%;
	z-index: 1;
}
}
.documentations .info-bar {
align-items: center;
border-bottom: 1px solid #dadee3;
display: flex;
height: 4.8125rem;
z-index: 1;
}
.documentations .info-bar label {
margin-left: 0.75rem;
margin-right: 0.75rem;
width: 100%;
}
.documentations .general-info {
background-color: #fff;
}
.documentations .general-info .actions {
display: block;
}
.documentations .general-info .actions svg {
height: 2.375rem;
width: 2.375rem;
}
@media (min-width: 768px) {
.documentations .general-info {
	position: fixed;
	width: 83.33333%;
	z-index: 1;
}
}
.documentations .mobile-doc-nav-toggler {
align-items: center;
border-bottom: 1px solid #ebeef2;
display: flex;
font-weight: 600;
justify-content: space-between;
padding: 0.5rem 0.25rem 0.5rem 1rem;
}
.documentations .mobile-doc-nav-toggler button {
color: #0b5fff;
}
.documentations .nav-link {
color: #30313f;
}
.documentations .nav-link.active {
color: #0b5fff;
font-weight: 600;
}
.documentations .toc {
position: fixed;
margin-top: 1.5rem;
max-width: 12%;
}

header {
align-items: center;
background-color: #184496;
color: #fff;
display: flex;
height: 3.5rem;
justify-content: space-between;
position: fixed;
right: 0;
top: 0;
width: 100%;
z-index: 2;
}
header a {
color: #fff;
}
header a:hover {
color: #fff;
}
header .brand {
align-items: center;
display: flex;
}
header .brand h2 {
margin: 0;
}
header .company-logo {
margin-left: 0.5rem;
margin-right: 0.5rem;
}
@media (min-width: 768px) {
header .company-logo {
	margin-left: 1.125rem;
}
}
header .header-links a {
font-size: 0.875rem;
margin-right: 3.25rem;
}
header .mobile-menu-wrapper {
align-items: center;
display: flex;
}
header .mobile-menu-wrapper.menu-open .header-links {
display: block;
}
header .mobile-menu-wrapper.menu-open .mobile-menu .lexicon-icon-bars {
display: none;
}
header .mobile-menu-wrapper.menu-open .mobile-menu .lexicon-icon-times {
display: block;
}
header .mobile-menu-wrapper.search-expanded .mobile-search {
display: none;
}
header .mobile-menu-wrapper.search-expanded .searchbox {
display: inline-block;
}
header .mobile-menu-wrapper .header-links {
background: #fff;
bottom: 0;
display: none;
font-weight: 600;
left: 0;
list-style: none;
margin-bottom: 0;
padding-inline-start: 0;
position: fixed;
top: 3.5rem;
right: 0;
}
header .mobile-menu-wrapper .header-links a {
align-items: center;
border-bottom: 1px solid #dadee3;
color: #30313f;
display: flex;
font-size: 1rem;
justify-content: space-between;
margin-right: 0;
padding: 1rem;
}
header .mobile-menu-wrapper .header-links li:last-child a {
border-bottom-width: 0;
}
header .mobile-menu-wrapper .mobile-menu {
cursor: pointer;
margin-right: 0.5rem;
padding: 0.75rem;
}
header .mobile-menu-wrapper .mobile-menu .lexicon-icon-times {
color: #fff;
display: none;
}
header .mobile-menu-wrapper .searchbox {
display: none;
margin-right: 0;
}
header .mobile-menu-wrapper .search-form {
width: 100%;
}
header .mobile-menu-wrapper .search-form label {
margin-bottom: 0;
}

.help-center-footer {
border-top: 1px solid #dadee3;
margin-bottom: 1rem;
margin-top: 2rem;
padding-top: 1rem;
}
.help-center-footer a strong {
font-weight: 600;
}
.help-center-footer h3 {
font-size: 20px;
margin-bottom: 0.75rem;
}
.help-center-footer .icon-container {
margin-top: 0.75rem;
}
.help-center-footer .liferay-waffle-icon {
height: 1.5rem;
margin-top: 0;
width: 1.5rem;
}

.landing-page-container .help-center-footer {
margin-left: 1rem;
margin-right: 1rem;
}

div.admonition.hilighting-alert {
border-left: 0;
font-size: 0.875rem;
margin: 0 16px 8px;
padding: 0.75rem 1.5rem 0.75rem 2.625rem;
}
div.admonition.hilighting-alert.hide {
display: none;
}
div.admonition.hilighting-alert .admonition-title {
display: flex;
margin: 0;
text-transform: none;
}
div.admonition.hilighting-alert .remove-link {
border-width: 0;
color: #0b5fff;
font-weight: normal;
padding: 0;
}
div.admonition.hilighting-alert .remove-link:hover {
color: #004ad7;
text-decoration: underline;
}
div.admonition.hilighting-alert .title-text {
transform: translateY(-2px);
}

.homepage .banner,
.homepage .products h2,
.homepage .resources h3 {
text-align: center;
}
.homepage .banner {
background-color: #f4f6f9;
padding: 5rem 0 11.875rem;
}
.homepage .card-disabled,
.homepage .notification-footnote {
opacity: 0.4;
}
.homepage .card-disabled .notification-dot {
position: absolute;
right: 0;
top: -0.5rem;
}
.homepage .cta-card,
.homepage .product-card {
background-color: #fff;
border: 1px solid rgba(9, 16, 24, 0.12);
border-radius: 0.25rem;
box-shadow: 0 6px 15px -6px rgba(9, 16, 29, 0.06);
color: #30313f;
margin-bottom: 1rem;
transition: transform 0.2s ease;
}
.homepage .cta-card:not(.card-disabled):hover,
.homepage .product-card:not(.card-disabled):hover {
border-bottom: 3px solid #0b5fff;
box-shadow: 0 6px 11px 0 rgba(9, 16, 29, 0.09);
margin-bottom: 0.75rem;
text-decoration: none;
transform: translateY(-4px);
}
.homepage .cta-card .description,
.homepage .product-card .description {
font-size: 0.875rem;
margin-bottom: 0;
}
.homepage .cta-card {
display: block;
padding: 1.5rem;
text-align: center;
}
@media (max-width: 768px) {
.homepage .cta-card {
	margin-bottom: 1.5rem;
}
.homepage .cta-card:hover {
	margin-bottom: 1.375rem;
}
}
.homepage .cta-card .icon {
height: 2.5rem;
width: 2.5rem;
}
.homepage .cta-card .title {
margin-top: 1.5rem;
}
.homepage .cta-links {
justify-content: center;
margin-bottom: 2.5rem;
}
.homepage .cta-links li {
display: flex;
flex-direction: column;
}
.homepage .cta-links ul {
display: flex;
flex-wrap: wrap;
list-style: none;
margin: 0;
padding: 0;
}
.homepage .cta-links .cta-card {
flex-grow: 1;
}
.homepage .homepage-search {
display: flex;
justify-content: center;
}
.homepage .homepage-search input[type=text] {
background-color: #fff;
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M499.2%20455.5%20377.7%20333.4c146-241.1-148.1-435.8-318.2-274-165.1%20185.9%2041.6%20460.6%20273.4%20319l121.5%20118.8c35.1%2038.6%2080-6.4%2044.8-41.7zm-293-391.9c191.9%200%20198.1%20289%200%20289-192.9%200-187.4-289%200-289z'%20fill='%230b5fff'/%3E%3C/svg%3E");
background-position: right 1.5rem center;
background-size: 1rem;
border: 1px solid #0b5fff;
border-bottom-left-radius: 0;
border-left-width: 0;
border-top-left-radius: 0;
color: #30313f;
height: 2.75rem;
padding: 0.75rem 1rem;
width: 25vw;
}
.homepage .homepage-search input[type=text]::placeholder {
color: #30313f;
}
.homepage .homepage-search input[type=text]:focus {
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M300.4%20256%20467%2089.4c29.6-29.6-14.8-74.1-44.4-44.4L256%20211.6%2089.4%2045C59.8%2015.3%2015.3%2059.8%2045%2089.4L211.6%20256%2045%20422.6c-29.7%2029.7%2014.7%2074.1%2044.4%2044.4L256%20300.4%20422.6%20467c29.7%2029.7%2074.1-14.7%2044.4-44.4L300.4%20256z'%20fill='%230b5fff'/%3E%3C/svg%3E");
}
@media (max-width: 991.98px) {
.homepage .homepage-search input[type=text] {
	width: 55vw;
}
}
.homepage .homepage-search .product-toggle {
border-bottom-right-radius: 0;
border-top-right-radius: 0;
padding: 0.5625rem 0.75rem;
}
.homepage .homepage-search .product-toggle svg {
pointer-events: none;
}
.homepage .homepage-search .product-toggle-wrapper {
position: relative;
}
.homepage .homepage-search .search-form {
width: auto;
}
.homepage .notification-footnote {
font-size: 0.875rem;
margin-top: 1.5rem;
}
.homepage .notification-footnote .notification-dot {
margin-right: 0.5rem;
}
.homepage .product-card {
margin-right: 1.5rem;
margin-top: 1.5rem;
min-height: 90px;
padding: 1.25rem 1.5rem;
}
@media (max-width: 991.98px) {
.homepage .product-card {
	min-height: auto;
}
}
.homepage .product-card .icon {
height: 2rem;
margin-right: 1rem;
width: 2rem;
}
.homepage .products {
margin-bottom: 2rem;
margin-top: -7.5rem;
}
@media (min-width: 768px) {
.homepage .products {
	margin-bottom: 5rem;
	margin-top: -9.25rem;
}
}
.homepage .resources h3 {
text-transform: uppercase;
margin-bottom: 2.125rem;
}
.homepage .sub-heading {
margin: 1.5rem 0 1.5rem;
}

.landing-page {
display: flex;
flex-wrap: wrap;
}
.landing-page .autofit-row-center > .autofit-col {
justify-content: initial;
}
.landing-page .icon {
height: 2rem;
margin-right: 1.5rem;
width: 2rem;
}
.landing-page .section-card {
border: 1px solid rgba(9, 16, 24, 0.12);
border-radius: 0.25rem;
box-shadow: 0 6px 15px -6px rgba(9, 16, 29, 0.06);
cursor: pointer;
margin-left: 1rem;
margin-right: 0.5rem;
margin-top: 1.5rem;
padding: 1.25rem 1.5rem;
transition: transform 0.2s ease;
width: 45%;
}
@media (max-width: 991.98px) {
.landing-page .section-card {
	margin-left: 0;
	margin-right: 0;
}
.landing-page .section-card:nth-child(odd) {
	margin-left: 0.5rem;
	margin-right: 1.25rem;
}
.landing-page .section-card .autofit-col-expand {
	margin-top: 1rem;
}
.landing-page .section-card .autofit-row {
	display: block;
}
}
.landing-page .section-card:hover {
border-bottom: 3px solid #0b5fff;
box-shadow: 0 6px 11px 0 rgba(9, 16, 29, 0.09);
margin-bottom: -3px;
text-decoration: none;
transform: translateY(-4px);
}
.landing-page .section-card:hover h4 {
text-decoration: none;
}
.landing-page .section-card h4 {
font-size: 1.125rem;
margin-bottom: 0.5rem;
}
.landing-page .section-card h4.sidebar {
border-left: 3px solid #42bfc2;
margin-left: -1.5rem;
padding-left: 1.25rem;
}
.landing-page .show-more {
color: #0053f0;
margin-left: -0.75rem;
}
.landing-page .subsection {
font-size: 0.875rem;
list-style: none;
padding-inline-start: 0;
}

.landing-page-container .article-body {
flex: initial;
max-width: 100%;
}
.landing-page-container .toc-container {
display: none;
}

.page-alert-container {
margin: 0 -15px;
}

.page-alert-hidden {
display: none;
}

.highlight-link {
display: none;
}

.search-form {
width: 232px;
}
.search-form input[type=text] {
background-color: rgba(255, 255, 255, 0.2);
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M499.2%20455.5%20377.7%20333.4c146-241.1-148.1-435.8-318.2-274-165.1%20185.9%2041.6%20460.6%20273.4%20319l121.5%20118.8c35.1%2038.6%2080-6.4%2044.8-41.7zm-293-391.9c191.9%200%20198.1%20289%200%20289-192.9%200-187.4-289%200-289z'%20fill='%23fff'/%3E%3C/svg%3E");
background-position: right 0.7rem center;
background-repeat: no-repeat;
background-size: 0.75rem;
border: 0;
border-radius: 0.25rem;
color: #fff;
font-size: 0.875rem;
height: 2.25rem;
padding: 0.5rem 1.25rem 0.5rem 1rem;
width: 100%;
}
.search-form input[type=text]::placeholder {
color: #fff;
}
.search-form input[type=text]:focus {
background-image: url("data:image/svg+xml;charset=utf8,%3Csvg%20xmlns='http://www.w3.org/2000/svg'%20viewBox='0%200%20512%20512'%3E%3Cpath%20class='lexicon-icon-outline'%20d='M300.4%20256%20467%2089.4c29.6-29.6-14.8-74.1-44.4-44.4L256%20211.6%2089.4%2045C59.8%2015.3%2015.3%2059.8%2045%2089.4L211.6%20256%2045%20422.6c-29.7%2029.7%2014.7%2074.1%2044.4%2044.4L256%20300.4%20422.6%20467c29.7%2029.7%2074.1-14.7%2044.4-44.4L300.4%20256z'%20fill='%23fff'/%3E%3C/svg%3E");
background-size: 0.625rem;
}

.search-page {
margin-top: 5.25rem;
}
.search-page h2 {
margin-top: 0;
}
.search-page ul.search li div.context {
margin: 8px 0;
}
.search-page .custom-radio {
margin-bottom: 8px;
}
.search-page .custom-radio .custom-control-label {
font-size: 0.875rem;
font-weight: 600;
padding-left: 0.5rem;
}
.search-page .filter-input-container {
padding-top: 1rem;
}
.search-page .filter-input-container:first-child {
padding-top: 1.5rem;
}
.search-page .filter-input-container label {
display: block;
}
.search-page .no-results {
display: none;
}
.search-page .no-results.show {
align-items: center;
display: flex;
flex-wrap: wrap;
}
.search-page .no-results svg {
margin-right: 1rem;
}
.search-page .search-form {
width: 100%;
}
.search-page .search-form input[type=text] {
background-color: #fff;
background-image: none;
border: 1px solid #dadee3;
color: #30313f;
width: 100%;
}
.search-page .search-form input[type=text]::placeholder {
color: #f8faff;
}
.search-page .search-filters {
background-color: #f4f6f9;
border-radius: 0.25rem;
margin-bottom: 0.75rem;
margin-right: 1.5rem;
padding: 1.5rem;
}
.search-page .search-filters h3 {
margin-top: 0;
}
@media (max-width: 768px) {
.search-page {
	margin-top: 1rem;
}
}

.search-results-wrapper {
display: flex;
}

.searchbox {
display: inline-block;
margin-right: 1rem;
}

.sd-card {
border: 1px solid rgba(9, 16, 24, 0.12);
border-radius: 0.25rem;
box-shadow: 0 6px 15px -6px rgba(9, 16, 29, 0.06) !important;
cursor: pointer;
font-size: 0.875rem;
margin-left: 1rem;
margin-right: 0.5rem;
margin-top: 1.5rem;
padding: 1.25rem 1.5rem;
transition: transform 0.2s ease;
}
.sd-card:hover {
border: 1px solid rgba(9, 16, 24, 0.12);
border-bottom: 3px solid #0b5fff;
box-shadow: 0 6px 11px 0 rgba(9, 16, 29, 0.09) !important;
cursor: pointer;
margin-bottom: -3px;
text-decoration: none;
transform: translateY(-4px);
}
.sd-card ul {
list-style: none;
padding-inline-start: 0;
}
.sd-card .sd-card-body {
padding: 0;
}
.sd-card .sd-card-title {
border-left: 3px solid #42bfc2;
font-size: 1.125rem;
font-weight: 600;
margin-left: -1.5rem;
padding-left: 1.25rem;
}

body .search-page .admonition {
margin-top: -5.25rem;
padding: 0;
}

div.body {
min-width: initial;
}

dt:target,
span.highlighted {
background-color: #c4d6f8;
}

pre {
background-color: inherit;
color: inherit;
}

table {
font-size: 0.875rem;
}
ul.search {
list-style: none;
margin: 1.5rem 0 0;
}
ul.search li {
background: none;
padding: 0 0 1.5rem;
}

.document .body {
color: #30313f;
max-width: initial;
}

.highlight {
border-radius: 0.25rem;
}