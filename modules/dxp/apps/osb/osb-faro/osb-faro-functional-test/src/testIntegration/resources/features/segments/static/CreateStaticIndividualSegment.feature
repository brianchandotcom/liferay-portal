@spira_Segments @Segments @Static @Creation @team_FARO @priority_5
Feature: Create a Static Individuals Segment
	As a Business User, I should be able to create a Static Individuals Segment

	Background: [Setup] Navigate to Individuals Segment Creation Page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page
		* I click the "Create Segment" button

	Scenario: Create the Static Individuals Segment
		Given I click the "Static Segment" dropdown option
		And I click the "Add Members" button
		And I search for "Willa"
		And I click the checkbox on the table row containing "Willa Watsica"
		And I search for "Alyse"
		And I click the checkbox on the table row containing "Alyse Cronin"
		And I click the "Add" button
		When I name the Static segment "CreateStaticIndividualsSegment - ${Random.1}" and save it
		And I go to the "Segments" page
		And I search for "CreateStaticIndividualsSegment - ${Random.1}"
		Then I should see a "Segment" named "CreateStaticIndividualsSegment - ${Random.1}" with "2" items