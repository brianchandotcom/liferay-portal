@spira_Segments @Segments @Dynamic @team_FARO @priority_5
Feature: Edit Individuals Segment Criteria
	As an Business User, I should be able to edit an Individuals Segment's Criteria

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| familyName | contains | raynor |
		* I name the Dynamic segment "EditIndividualsSegmentCriteria - ${Random.1}" and save it

	Scenario: Edit Individuals Segment Criteria
		Given I go to the "Segments" page
		And I search for "EditIndividualsSegmentCriteria - ${Random.1}"
		And I click "EditIndividualsSegmentCriteria - ${Random.1}" in the table
		When I click the "Edit Segment" button
		And I delete the criteria group in row 1
		And I select "Individual Attributes" from the criterion type dropdown
		And I create a criteria with the following conditions:
			| familyName | is | will |
		And I click the "Save Segment" button
		And I go to the "Segments" page
		And I search for "EditIndividualsSegmentCriteria - ${Random.1}"
		Then I should see a "Segment" named "EditIndividualsSegmentCriteria - ${Random.1}" with "1" items