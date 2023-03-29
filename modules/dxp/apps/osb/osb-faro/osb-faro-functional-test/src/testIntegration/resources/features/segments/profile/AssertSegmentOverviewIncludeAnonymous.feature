@component_Segments @team_FARO @priority_3
Feature: Assert Segment Overview shows include anonymous
	As a Business User, I should be able to assert segment overview shows include anonymous

	Background: [Setup] Create a dynamic segment with anonymous
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
		| additionalName | is | bert |
		* I click the Include Anonymous switch
		* I name the Dynamic segment "CreateDynamicIndividualsSegment - ${Random.1}" and save it

	Scenario: Assert Segment Overview include anonymous
		Given I go to the "Segments" page
		When I click "CreateDynamicIndividualsSegment - ${Random.1}" in the table
		And I should see the Segment Profile Cards with their details
		Then I should see Include Anonymous label