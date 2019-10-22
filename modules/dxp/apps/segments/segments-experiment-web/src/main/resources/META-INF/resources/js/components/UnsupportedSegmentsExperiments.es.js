import React from 'react';
import FlaskIllustration from './FlaskIllustration.es';

export default function UnsupportedSegmentsExperiments() {
	return (
		<div className="p-3 d-flex flex-column align-items-center">
			<FlaskIllustration />

			<h4 className="text-center text-dark">
				{Liferay.Language.get(
					'ab-test-is-available-only-for-content-pages'
				)}
			</h4>
		</div>
	);
}
