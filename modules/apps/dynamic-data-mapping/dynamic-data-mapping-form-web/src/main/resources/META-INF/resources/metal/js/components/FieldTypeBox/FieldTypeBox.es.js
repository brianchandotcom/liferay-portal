const FieldTypeBox = ({spritemap, fieldType, fieldId}) => {
	return (
		<div
			class="ddm-drag-item list-group-item list-group-item-flex"
			data-ddm-field-type-index={fieldId}
			key={`field${fieldId}`}
			ref={`field${fieldId}`}
		>
			<div class="autofit-col">
				<div class="sticker sticker-secondary">
					<svg
						aria-hidden="true"
						class={`lexicon-icon lexicon-icon-${
							fieldType.icon
						}`}
					>
						<use
							xlink:href={`${spritemap}#${fieldType.icon}`}
						/>
					</svg>
				</div>
			</div>
			<div class="autofit-col autofit-col-expand">
				<h4 class="list-group-title text-truncate">
					<span>{fieldType.label}</span>
				</h4>
				{fieldType.description && (
					<p class="list-group-subtitle text-truncate">
						{fieldType.description}
					</p>
				)}
			</div>
		</div>
	);
};

export default FieldTypeBox;