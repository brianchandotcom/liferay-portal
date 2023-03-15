@spira_Segments @Segments @Static @Deletion @team_FARO @priority_5
Feature: Delete a Static Individuals Segment
	As an Business User, I should be able to delete a Static Individuals Segment

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Alyse Cronin"
		* I click the checkbox on the table row containing "Alyse Cronin"
		* I click the "Add" button
		* I click the "View Added Members" button
		* I name the Static segment "DeleteStaticIndividualsSegment - ${Random.1}" and save it

	Scenario: Delete a Static Individuals Segment
		Given I go to the "Segments" page
		And I search for "DeleteStaticIndividualsSegment - ${Random.1}"
		And I click "DeleteStaticIndividualsSegment - ${Random.1}" in the table
		And I click the "Edit Segment" button
		When I click the "Delete Segment" button
		And I click the "Delete" button
		And I go to the "Segments" page
		Then I should not see an item named "DeleteStaticIndividualsSegment - ${Random.1}" in the table