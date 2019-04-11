import React from 'react';
import {sub} from '../../utils/utils.es';

export default function EmptyPlaceholder() {
	return (
		<div className="taglib-empty-result-message mb-0 p-5 rounded empty-contributors">
			<div className="taglib-empty-result-message-header" />
			<div className="sheet-text text-center">
				<h1 className="mb-3 mt-4">{sub(Liferay.Language.get('no-x-yet'), [Liferay.Language.get('conditions')])}</h1>
				<p>{Liferay.Language.get('empty-conditions-message')}</p>
			</div>
		</div>
	);
}