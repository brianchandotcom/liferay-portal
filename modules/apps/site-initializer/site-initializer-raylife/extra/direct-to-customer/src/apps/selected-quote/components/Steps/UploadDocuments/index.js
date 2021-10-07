import UploadFiles from './UploadFiles';

const sections = [
	{
		required: true,
		subtitle: 'Upload a copy of your business license',
		title: 'Business License',
		type: 'document',
	},
	{
		required: false,
		subtitle: 'Upload a copy of your additional documents.',
		title: 'Additional Documents',
		type: 'document',
	},
	{
		required: true,
		subtitle: 'Upload 4 photos of your building interior',
		title: 'Building Interior Photos',
		type: 'image',
	},
];

const dropAreaProps = {
	heightContainer: '120px',
	limitFiles: 4,
	widthContainer: '100%',
};

const UploadDocuments = () => {
	const onClickConfirmUpload = () => {};

	return (
		<div className="upload-container">
			{sections.map((section, index) => (
				<div className="upload-section" key={index}>
					<div className="header">
						<h3 className="title">
							{section.title}
							{section.required ? (
								<span className="required">*</span>
							) : (
								<span className="optional">(optional)</span>
							)}
						</h3>

						<span className="subtitle">{section.subtitle}</span>
					</div>

					<div className="upload-content">
						<UploadFiles
							dropAreaProps={{
								...dropAreaProps,
								type: section.type,
							}}
							title={section.title}
						/>
					</div>
				</div>
			))}
			<div className="upload-footer">
				<button onClick={onClickConfirmUpload}>CONFIRM UPLOADS</button>
			</div>
		</div>
	);
};

export default UploadDocuments;
