import ClayIcon from '@clayui/icon';

const ViewFilesPanel = ({sections}) => (
	<>
		{sections?.map((section, index) => (
			<div className="view-section" key={index}>
				<h3>{section.title.toUpperCase()}</h3>

				<div className="content">
					{section.files.map((file) => {
						const isImage = file.type.includes('image');

						if (isImage) {
							return (
								<div className="image">
									<img
										alt={file.name}
										key={index}
										src={file.fileURL}
									/>
								</div>
							);
						} else {
							return (
								<div className="documents">
									<ClayIcon
										className={file.icon}
										key={index}
										symbol={file.icon}
									/>
								</div>
							);
						}
					})}
				</div>
			</div>
		))}
	</>
);

export default ViewFilesPanel;
