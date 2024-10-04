Facet:
	properties:
		facetCriteria:
			type: string
		facetValues:
			items:
				$ref: '#/components/schemas/FacetValue'
			type: array
	type: object
FacetValue:
	properties:
		numberOfOccurrences:
			format: int32
			type: integer
		term:
			type: string
	type: object