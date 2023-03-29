@spira_Segments @Segments @Static @Creation @team_FARO @priority_5
Feature: Add a User to a Static Segment
	As an Business User, I should be able to add a user to a Static Individuals Segment

	Background: [Setup] Create a Static Individuals Segment
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I click "Segments" in the sidebar
		* I should see the "Segments" page
		* I click the "Create Segment" button
		* I click the "Static Segment" dropdown option
		* I click the "Add Members" button
		* I search for "Alton Jacobi"
		* I click the checkbox on the table row containing "Alton Jacobi"
		* I click the "Add" button
		* I click the "View Added Members" button
		* I name the Static segment "AddUserToStaticSegment - ${Random.1}" and save it

	Scenario: Add a User to the Static Segment
		Given I go to the "Individuals" page
		And I click the "Known Individuals" tab
		And I search for "Arlen Gottlieb"
		And I click the checkbox on the table row containing "Arlen Gottlieb"
		When I click the "Add to Static Segment" button
		And I click the checkbox on the table row containing "AddUserToStaticSegment - ${Random.1}"
		And I click the "Add" button
		Then I should see a success alert
		When I go to the "Segments" page
		Then I should see a "Segment" named "AddUserToStaticSegment - ${Random.1}" with "2" items