Facet:
	properties:
		facetCriteria:
			type: string
		facetValues:
			type: array
			items:
				$ref: '#/components/schemas/FacetValue'
	type: object
FacetValue:
	properties:
		numberOfOccurrences:
			type: integer
			format: int32
		term:
			type: string
	type: object