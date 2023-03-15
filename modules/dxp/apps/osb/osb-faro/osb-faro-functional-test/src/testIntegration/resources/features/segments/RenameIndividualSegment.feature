@spira_Segments @Segments @team_FARO @priority_5
Feature: Rename Individuals Segment
	As an Business User, I should be able to rename Individuals Segments

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Alyse"
		* I click the checkbox on the table row containing "Alyse Cronin"
		* I click the "Add" button
		* I name the Static segment "RenameIndividualSegment - ${Random.1}" and save it

	Scenario: Rename Individuals Segment
		Given I go to the "Segments" page
		And I click "RenameIndividualSegment - ${Random.1}" in the table
		When I click the "Edit Segment" button
		And I name the Static segment "RenameIndividualSegment_EDITED - ${Random.1}" and save it
		And I go to the "Segments" page
		And I search for "RenameIndividualSegment_EDITED - ${Random.1}"
		Then I should see a "Segment" named "RenameIndividualSegment_EDITED - ${Random.1}" with "1" items