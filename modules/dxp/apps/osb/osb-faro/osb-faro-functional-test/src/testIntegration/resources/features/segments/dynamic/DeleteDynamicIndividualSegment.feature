@spira_Segments @Segments @Dynamic @Deletion @team_FARO @priority_5
Feature: Delete a Dynamic Individuals Segment
	As an Business User, I should be able to delete a Dynamic Individuals Segment

	Background: [Setup] Create a Dynamic Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Dynamic Segment" dropdown option
		* I select "Individual Attributes" from the criterion type dropdown
		* I create a criteria with the following conditions:
			| familyName | contains | raynor |
		* I name the Dynamic segment "DeleteDynamicIndividualsSegment - ${Random.1}" and save it

	Scenario: Delete a Static Individuals Segment
		Given I go to the "Segments" page
		And I search for "DeleteDynamicIndividualsSegment - ${Random.1}"
		And I click "DeleteDynamicIndividualsSegment - ${Random.1}" in the table
		And I click the "Edit Segment" button
		When I click the "Delete Segment" button
		And I click the "Delete" button
		And I go to the "Segments" page
		Then I should not see an item named "DeleteDynamicIndividualsSegment - ${Random.1}" in the table