@component_Segments @team_FARO @priority_3
Feature: Assert segment overview shows criteria
	As a Business User, I should be able to assert segment overview shows criteria

	Background: [Setup] Create a dynamic segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| state | is | california |
		* I name the Dynamic segment "CreateDynamicIndividualsSegment - ${Random.1}" and save it

	Scenario: Assert segment overview shows criteria
		Given I go to the "Segments" page
		When I click "CreateDynamicIndividualsSegment - ${Random.1}" in the table
		Then I should see the Segment Criteria with the following details:
			| Individual | state | is | california |