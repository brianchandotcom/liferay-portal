@spira_Segments @Segments @Static @team_FARO @priority_4
Feature: Undo Deleting a Segment Member in the Edit Modal
	As an Business User, I should be able to undo deleting a member when editing a Static Segment

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Willa"
		* I click the checkbox on the table row containing "Willa Watsica"
		* I search for "Alyse"
		* I click the checkbox on the table row containing "Alyse Cronin"
		* I click the "Add" button
		* I name the Static segment "UndoDeletingSegmentMember - ${Random.1}" and save it

	Scenario: Undo Deleting a Segment Member in the Edit Modal
		Given I go to the "Segments" page
		And I search for "UndoDeletingSegmentMember - ${Random.1}"
		And I click "UndoDeletingSegmentMember - ${Random.1}" in the table
		And I click the "Edit Segment" button
		And I click the checkbox on the table row containing "Alyse Cronin"
		And I click the "Remove Members" button
		And I click the "Undo Changes" button
		And I name the Static segment "UndoDeletingSegmentMember - ${Random.1}" and save it
		And I click the "Membership" tab
		Then I should see an Individual named "Alyse Cronin" in the table