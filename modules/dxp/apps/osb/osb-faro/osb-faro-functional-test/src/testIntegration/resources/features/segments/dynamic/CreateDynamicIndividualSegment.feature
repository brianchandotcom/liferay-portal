@spira_Segments @Segments @Dynamic @Creation @team_FARO @priority_5
Feature: Create a Dynamic Individuals Segment
	As a Business User, I should be able to create a Dynamic Individuals Segment

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Create the Dynamic Individuals Segment
		Given I click the "Dynamic Segment" dropdown option
		And I select "Individual Attributes" from the criterion type dropdown
		When I create a criteria with the following conditions:
			| givenName | contains | abram |
		And I name the Dynamic segment "CreateDynamicIndividualsSegment - ${Random.1}" and save it
		When I go to the "Segments" page
		And I search for "CreateDynamicIndividualsSegment - ${Random.1}"
		Then I should see a "Segment" named "CreateDynamicIndividualsSegment - ${Random.1}" with "1" items