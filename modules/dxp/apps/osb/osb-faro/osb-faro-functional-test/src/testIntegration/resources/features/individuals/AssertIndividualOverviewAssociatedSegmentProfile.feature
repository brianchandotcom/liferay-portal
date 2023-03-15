@spira_Individuals @component_Individuals @team_FARO @priority_3
Feature: Clicking an associated segment navigates to segment profile
	As a Business User, I should be able to click an associated segment

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Open associated segment navigate to segment profile
		Given I click the "Known Individuals" tab
		And I click "Abram Bauch" in the table
		When I select "everybody" in the card list "Associated Segments"
		Then I should see the Segment Profile Cards with their details