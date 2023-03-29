@spira_Segments @Segments @team_FARO @priority_3
Feature: Assert top interest shown in segment profile
	As a Business User, I should be able to view top interest in segment profile

	Background: [Setup] Navigate to Segments page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Segments" page

	Scenario: Assert top interest shown in segment profile
		Given I click "everybody" in the table
		Then I should see "rich e-commerce" in the card list "Top Interests"