import React from 'react';
import ReactDOM from 'react-dom';
import SegmentEdit from './components/segment_edit/SegmentEdit.es';
import ThemeContext from './ThemeContext.es';

export default function(id, props, context) {
	ReactDOM.render(
		<ThemeContext.Provider value={context}>
			<div className='segments-root'>
				<SegmentEdit {...props} />
			</div>
		</ThemeContext.Provider>,
		document.getElementById(id)
	);
}
