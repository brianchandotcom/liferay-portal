import React from 'react';
import {AssetIcon, MimeTypes} from 'assets/components/AssetsIcon';

const AssetsIconKit: React.FC = () => (
	<div>
		<h4>{'Assets Icon'}</h4>

		<div className='d-flex flex-wrap' style={{gap: '1rem'}}>
			{Object.values(MimeTypes).map(mimeType => (
				<div
					className='align-items-center d-flex flex-column'
					key={mimeType}
					style={{gap: '0.5rem'}}
				>
					<AssetIcon mimeType={mimeType} />

					<small>{mimeType}</small>
				</div>
			))}
		</div>
	</div>
);

export default AssetsIconKit;
