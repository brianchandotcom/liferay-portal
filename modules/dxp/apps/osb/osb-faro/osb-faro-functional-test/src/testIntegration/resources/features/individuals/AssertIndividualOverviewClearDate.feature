@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Selected individual activity point can clear date selection
	As a Business User, I should be able to clear date selection from individual activity

	Background: [Setup] Navigate to Individuals
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Select clear date selection
		Given I click the "Known Individuals" tab
		And I click "Adrienne Johnston" in the table
		When I click row number "1" in the bar graph table
		And I should see text saying "Visited www.horacio-kulas.com" on the page
		And I click the "Clear Date Selection" button
		Then I should see an activity named "Visited www.dino-boyer.com" in the table